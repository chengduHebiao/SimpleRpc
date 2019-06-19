/**
 * Inc All Rights Reserved @2018
 */

package com.cn.schedule;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author hebiao
 * @version $Id:HostUtils.java, v0.1 2018/7/12 11:19 hebiao Exp $$ 
 */
public class HostUtils {

    private static final Logger logger = LoggerFactory.getLogger(HostUtils.class);

    /**
     * 获取服务器IP地址
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String> getServerIpList() {
        List<String> serverIpList = new ArrayList<>();
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                if (!inetAddresses.hasMoreElements()) {
                    continue;
                }
                InetAddress ip = inetAddresses.nextElement();
                if (StringUtils.isNotEmpty(ip.getHostAddress()) && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {
                    serverIpList.add(ip.getHostAddress());
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        logger.info("获取到当前server的ip为：{}", serverIpList);
        return serverIpList;
    }

    public static String getServerIp() {
        return getServerIpList().get(0);
    }

    public static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }


    public static String getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return StringUtils.substringBefore(name, "@");
    }

    private static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }


}
