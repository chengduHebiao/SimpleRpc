/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.rpc.zookeeper;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 *
 *
 * @author hebiao
 * @version $Id:ZookeeperWatcher.java, v0.1 2018/8/22 16:59 hebiao Exp $$ 
 */
public class ZookeeperWatcher implements Watcher {

  private CountDownLatch countDownLatch;
  public ZookeeperWatcher(CountDownLatch countDownLatch){

    this.countDownLatch = countDownLatch;
  }
  @Override
  public void process(WatchedEvent watchedEvent) {

    if(watchedEvent.getState() == KeeperState.SyncConnected){

      countDownLatch.countDown();
    }

  }
}
