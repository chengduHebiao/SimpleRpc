
package com.cn.hebiao;

import java.util.concurrent.Semaphore;

/**
 * @author hebiao
 * @version $Id:ThreadTest.java, v0.1 2018/9/2 16:04 hebiao Exp $$
 */
public class ThreadTest {

  private Integer maxProcess = 1;
  private Integer threadCount = 0;
  Semaphore semaphore = new Semaphore(maxProcess);

  public void solve(Integer count) {

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  private void doHandler() {

    try {
      semaphore.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(Thread.currentThread().getName() + "进入了程序");
    solve(threadCount);

    semaphore.release();
    System.out.println(Thread.currentThread().getName() + "离开了程序");

  }

  public static void main(String[] args) {
    ThreadTest test = new ThreadTest();
    for (int i = 0; i < 10; i++) {
      new Thread(() -> test.doHandler()).start();
    }
  }
}
