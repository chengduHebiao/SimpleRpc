/**
 * Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

/**
 *
 *
 * @author hebiao
 * @version $Id:RealHandler1.java, v0.1 2018/7/6 16:29 hebiao Exp $$ 
 */
public class RealHandler1 extends Handler {

  @Override
  public void handle(int requestId) {
    if(requestId>10){
      System.out.println("我的handler1，我已处理请求"+requestId);
    }
    else{
      this.handler.handle(requestId);
    }
  }
}
