/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.rpc.zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 基于zk实现分布式锁
 *
 * @author hebiao
 * @version $Id:ZookeeperLock.java, v0.1 2018/10/29 14:25 hebiao Exp $$
 */
public class ZookeeperLock implements Watcher {

    public static String host = "127.0.0.1:2181";
    //缓存时间
    private static final int TIME_OUT = 200000;

    private static String FATHER_PATH = "/disLocks1";

    private ZooKeeper zk;

    private int threadId;

    protected CountDownLatch countDownLatch = new CountDownLatch(1);

    public ZookeeperLock(int threadId) {
        this.threadId = threadId;
    }

    //获取zk连接
    public void getZkClient(String host, int timeout) {
        try {
            if (null == zk) {
                zk = new ZooKeeper(host, timeout, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建子节点
     */
    public String createNode() {
        try {
            //检测节点是否存在
            Stat stat = zk.exists(FATHER_PATH, false);
            //父节点不存在，则创建父节点，防止多线程并发创建父节点，所以加上同步代码块，防止在同一个jvm中的并发创建，多jvm环境下， 父节点可以事先创建好
            if (Objects.isNull(stat)) {
                synchronized (FATHER_PATH) {
                    Stat stat2 = zk.exists(FATHER_PATH, false);
                    if (Objects.isNull(stat2)) {
                        //父节点是持久节点
                        String path = zk.create(FATHER_PATH, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        System.out.println("父节点创建成功，返回值【" + path + "】");
                    }

                }
            }
            //创建持久性父节点下面的临时顺序子节点,/父节点路径/0000000002
            String lockPath = zk.create(FATHER_PATH + "/", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("线程【" + threadId + "】开始执行,子节点创建成功，返回值【" + lockPath + "】");
            return lockPath;
        } catch (KeeperException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    //校验当前节点是否为序号最小的节点
    public boolean checkLockPath(String lockPath) {
        try {
            //注册父节点监听事件,当父节点下面的子节点有变化，就会触发Watcher事件
            List<String> nodeList = zk.getChildren(FATHER_PATH, this);
            Collections.sort(nodeList);
            int index = nodeList.indexOf(lockPath.substring(FATHER_PATH.length() + 1));
            switch (index) {
                case -1: {
                    System.out.println("本节点已不在了" + lockPath);
                    return false;
                }
                case 0: {
                    System.out.println("线程【" + threadId + "】获取锁成功，子节点序号【" + lockPath + "】");
                    return true;
                }
                default: {
                    String waitPath = nodeList.get(index - 1);
                    System.out.println(waitPath + "在" + nodeList.get(index) + "点前面,需要等待【" + nodeList.get(index) + "】");
                    return false;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getLock() {
        //创建获取锁的节点（顺序临时节点）
        String childPath = createNode();
        boolean flag = true;
        if (null != childPath) {
            try {
                //轮询等待zk获取锁的通知
                while (flag) {
                    if (checkLockPath(childPath)) {
                        //获取锁成功
                        return true;
                    } else {
                        //节点创建成功， 则等待zk通知
                        countDownLatch.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("节点没有创建成功，获取锁失败");
        }
        return false;
    }

    public void process(WatchedEvent event) {
        //成功连接zk，状态判断
        if (event.getState() == KeeperState.SyncConnected) {
            //子节点有变化
            if (event.getType() == EventType.NodeChildrenChanged) {
                countDownLatch.countDown();
            }
        }
    }

    public void unlock() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public ZooKeeper getZooKeeper() {
        return zk;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int threadId = i + 1;
            new Thread(() -> {
                try {
                    ZookeeperLock dis = new ZookeeperLock(threadId);
                    dis.getZkClient(host, TIME_OUT);
                    if (dis.getLock()) {
                        Thread.sleep(200);
                        dis.unlock();
                    }
                } catch (Exception e) {
                    System.out.println("【第" + threadId + "个线程】 抛出的异常：");
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
