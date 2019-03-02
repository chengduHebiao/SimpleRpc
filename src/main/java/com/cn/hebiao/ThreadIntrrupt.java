/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:ThreadIntrrupt.java, v0.1 2019/1/30 11:00 hebiao Exp $$
 */
public class ThreadIntrrupt {

    public static class intrruptTest extends Thread {

        @Override
        public void run() {

            while (!isInterrupted()) {
                try {
                    Thread.sleep(3000);
                    System.out.println("Thread " + currentThread().getName() + " is running");
                } catch (InterruptedException e) {

                    e.printStackTrace();
                    System.out.println("Thread " + currentThread().getName() + " has interrupted");
                    System.out.println("Thread " + currentThread().getName() + " flag is " + isInterrupted());
                    interrupt();
                }
            }


        }
    }


    public static void main(String[] args) {

        intrruptTest test1 = new intrruptTest();

        test1.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test1.interrupt();

    }
}
