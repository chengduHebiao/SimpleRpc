/**
 * All Rights Reserved @2018
 */

package com.cn.rpc.zookeeper;

/**
 * @author hebiao
 * @version $Id:ZookeeperCreateFactory.java, v0.1 2018/8/22 18:18 hebiao Exp $$
 */
public class ZookeeperCreateFactory {

    public static ZookeeperRegister zookeeperRegister = null;

    public static ZookeeperRegister createZookeeper() {
        synchronized (ZookeeperCreateFactory.class) {
            if (zookeeperRegister == null) {
                zookeeperRegister = new ZookeeperRegister();
            }
        }

        return zookeeperRegister;
    }

    public static ZookeeperRegister getZookeeper() {

        if (zookeeperRegister == null) {
            createZookeeper();
        }
        return zookeeperRegister;
    }

}
