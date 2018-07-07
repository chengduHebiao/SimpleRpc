/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:HandlerTest.java, v0.1 2018/7/6 16:37 hebiao Exp $$
 */
public class HandlerTest {

  public static void main(String[] args) {

    Handler handler1 = new RealHandler1();
    Handler handler2 = new RealHandler2();
    handler1.setHandler(handler2);
    int[] requestId = {2, 12, 9, 7, 0,10};

    for (int i : requestId) {
      handler1.handle(i);
    }
  }

}
