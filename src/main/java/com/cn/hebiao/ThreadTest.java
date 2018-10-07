
package com.cn.hebiao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hebiao
 * @version $Id:ThreadTest.java, v0.1 2018/9/2 16:04 hebiao Exp $$
 */
public class ThreadTest {


  private CountDownLatch countDownLatch;

  public ThreadTest(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  public ThreadTest() {
  }

  private int count = 0;

  public void method1() {
    synchronized (this) {
      System.out.println("线程" + Thread.currentThread().getName() + "进入了方法");
      count++;
      try {
        Thread.sleep(2000L);
        System.out.println("count:" + count);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  //同一对象实例上的锁，访问其成员变量方法是互斥，不同对象实例的则无法达到互斥的效果
  public synchronized void method2() {

    count++;

    System.out.println("count:" + count);

  }

  //类所有对象实例调用都达到互斥的效果
  public static synchronized void method3() {

  }

  public static void method4() {
    synchronized (ThreadTest.class) {

    }
  }

  public void increment() {
    synchronized (this) {
      count++;
      countDownLatch.countDown();
    }
  }

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
  /*  ThreadTest test = new ThreadTest();
    ThreadTest test1 = new ThreadTest();
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        test.method1();
        test1.method2();

      }).start();
*/
      ExecutorService executorService = Executors.newCachedThreadPool();//注意，使用时有风险

      ExecutorService executorService1 = Executors.newFixedThreadPool(5);

      int coreSize = Runtime.getRuntime().availableProcessors()*2;
      ExecutorService service  = new ThreadPoolExecutor(coreSize,Integer.MAX_VALUE,10, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(coreSize));

      ExecutorService executorService2 = Executors.newSingleThreadExecutor();
      for (int i=0;i<100;i++ ) {
        service.execute(() -> System.out.println("thread" + Thread.currentThread().getName()));
      }

      service.shutdown();
    }
  }

