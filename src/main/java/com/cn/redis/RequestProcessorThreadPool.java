/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hebiao
 * @version $Id:RequestProcessorThreadPool.java, v0.1 2018/11/12 14:16 hebiao Exp $$
 */
public class RequestProcessorThreadPool {

    private static final int blockingQueueNum = SysConfigUtil.get("request.blockingqueue.number") == null ? 10
            : Integer.valueOf(SysConfigUtil.get("request.blockingqueue.number").toString());

    private static final int queueDataNum = SysConfigUtil.get("request.everyqueue.data.length") == null ? 100
            : Integer.valueOf(SysConfigUtil.get("request.everyqueue.data.length").toString());

    private ExecutorService threadPool = Executors.newFixedThreadPool(blockingQueueNum);

    private RequestProcessorThreadPool() {

        for (int i = 0; i < blockingQueueNum; i++) {//初始化队列

            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(queueDataNum);//每个队列中放100条数据

            RequestQueue.getInstance().addQueue(queue);

            threadPool.submit(new RequestProcessorThread(queue));//把每个queue交个线程去处理，线程会处理每个queue中的数据

        }

    }

    public static RequestProcessorThreadPool getInstance() {

        return Singleton.getInstance();

    }

    /**
     * 初始化线程池
     */

    public static void init() {

        getInstance();

    }

    public static class Singleton {

        private static RequestProcessorThreadPool instance;

        static {

            instance = new RequestProcessorThreadPool();

        }

        public static RequestProcessorThreadPool getInstance() {

            return instance;

        }

    }
}
