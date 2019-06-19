/**
 * All Rights Reserved @2018
 */
package com.cn.rpc.zookeeper;

import static org.apache.zookeeper.CreateMode.PERSISTENT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * zookeeper做注册中心
 *
 * @author hebiao
 * @version $Id:ZookeeperRegister.java, v0.1 2018/8/22 16:22 hebiao Exp $$
 */
public class ZookeeperRegister {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperRegister.class);
    private static List<String> dataList = new ArrayList<>();
    private String url = "127.0.0.1";
    private Integer port = 2181;
    private ZooKeeper zooKeeper;
    private String BASE_ROOT = "SIMPLEPC";
    private Integer DEFAULT_ZK_SESSION_TIMEOUT = 500000;
    private CountDownLatch latch = new CountDownLatch(1);

    public ZookeeperRegister() {
        init();
    }

    public ZookeeperRegister(String url, Integer port) {
        this.url = url;
        this.port = port;
        init();
    }

    private void init() {
        zooKeeper = connectServer();

    }

    /**
     * 创建节点
     */
    public void createNode(String name, String host) {
        String path = "/" + BASE_ROOT;
        if (exist(path)) {
            logger.warn("节点{}已经存在", path);
        } else {
            try {
                zooKeeper.create(path, "simple".getBytes(), Ids.OPEN_ACL_UNSAFE, PERSISTENT);//创建节点
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (!exist(path + "/" + name)) {
                zooKeeper.create(path + "/" + name, name.getBytes(), Ids.OPEN_ACL_UNSAFE, PERSISTENT);
            }

            if (!exist(path + "/" + name + "/" + host)) {
                zooKeeper.create(path + "/" + name + "/" + host, host.getBytes(), Ids.OPEN_ACL_UNSAFE, PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (zooKeeper != null) {
            watchNode(zooKeeper);
        }

    }

    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1) {
                data = dataList.get(0);

            } else {
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));

            }
        }
        return data;
    }

    public String findService(String serviceName) {

        if (StringUtils.isEmpty(serviceName)) {
            throw new IllegalArgumentException("参数错误");
        }

        List<String> nodeList = null;
        try {
            nodeList = zooKeeper.getChildren("/" + BASE_ROOT + "/" + serviceName, false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.warn("zk serviceName 为{}的服务节点有{}", serviceName, nodeList);
        return nodeList.get(0);
    }

    private boolean exist(String path) {
        boolean exist = false;
        try {
            exist = zooKeeper.exists(path, null) != null;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exist;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(url + ":" + port, DEFAULT_ZK_SESSION_TIMEOUT, new ZookeeperWatcher(latch));
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.warn("zk连接{}", zk);
        return zk;
    }

    /**
     * 监听node
     */
    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren("/" + BASE_ROOT, event -> {
                if (event.getType() == Event.EventType.NodeChildrenChanged) {
                    watchNode(zk);
                }
            });
            logger.warn("zk 节点有{}", nodeList);
            List<String> dataList = new ArrayList<>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData("/" + BASE_ROOT + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
            this.dataList = dataList;
            if (dataList.isEmpty()) {
                throw new RuntimeException("尚未注册任何服务");
            }
        } catch (Exception e) {
            logger.error("发现节点异常", e);
        }
    }


}
