/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:RealHandler2.java, v0.1 2018/7/6 16:33 hebiao Exp $$
 */
public class RealHandler2 extends Handler {

  @Override
  public void handle(int requestId) {
    if (requestId < 10) {
      System.out.println("我的handler2，处理了请求" + requestId);
    } else {
      System.out.println("无人能处理请求" + requestId);
    }
  }
}
