/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

import java.util.concurrent.ArrayBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hebiao
 * @version $Id:RequestAsyncProcessBizImpl.java, v0.1 2018/11/12 14:21 hebiao Exp $$
 */
public class RequestAsyncProcessBizImpl implements IRequestAsyncProcessBiz {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override

    public void process(Request request) {

        // 做请求的路由，根据productId路由到对应的队列

        ArrayBlockingQueue<Request> queue = getQueueByProductId(request.getProductId());

        try {

            queue.put(request);

        } catch (InterruptedException e) {

            logger.error("产品ID{}加入队列失败", request.getProductId(), e);

        }

    }


    private ArrayBlockingQueue<Request> getQueueByProductId(Integer productId) {

        RequestQueue requestQueue = RequestQueue.getInstance();

        String key = String.valueOf(productId);

        int hashcode;

        int hash = (key == null) ? 0 : (hashcode = key.hashCode()) ^ (hashcode >>> 16);

        //对hashcode取摸

        int index = (requestQueue.getQueueSize() - 1) & hash;

        return requestQueue.getQueueByIndex(index);

    }


}
