/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.rpc.interfaces;

import com.cn.rpc.annotation.Rpc;
import org.springframework.stereotype.Service;

/**
 * @author hebiao
 * @version $Id:HelloServiceImpl.java, v0.1 2018/5/31 22:19 hebiao Exp $$
 */
@Rpc(value = "com.cn.rpc.interfaces.HelloService")
@Service
public class HelloServiceImpl implements HelloService {

  public String sayHello(String message) {
    return "hello" + message;
  }
}
