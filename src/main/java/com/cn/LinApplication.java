package com.cn;

import com.cn.rpc.remote.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hebiao
 * @version $Id:LinApplication.java, v0.1 2018/6/1 11:06 hebiao Exp $$
 */
@SpringBootApplication
public class LinApplication implements CommandLineRunner {

    @Autowired
    private RpcServer rpcServer;

    public static void main(String[] args) {

        SpringApplication.run(LinApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        rpcServer.afterPropertiesSet();
    }
}
