/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 *
 * @author hebiao
 * @version $Id:SemaphoreDemo.java, v0.1 2018/7/13 13:55 hebiao Exp $$
 */
public class SemaphoreDemo {


  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(30);
    final Semaphore semaphore = new Semaphore(5);

    for (int i = 0; i < 30; i++) {
      executorService.execute(() -> {
        try {
          // 获取许可
          semaphore.acquire();
          System.out.println(Thread.currentThread().getName() + "线程持有信号量");
          // 模拟请求服务器资源
          Thread.sleep(2000);
          System.out.println("当前可用的信号量为:" + semaphore.availablePermits());
          // 访问完后，释放资源
          System.out.println(Thread.currentThread().getName() + "线程释放信号量");
          semaphore.release();
          System.out.println("当前可用的信号量为:" + semaphore.availablePermits());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executorService.shutdown();

  }


}
