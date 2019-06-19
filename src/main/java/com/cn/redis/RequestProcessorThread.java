/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author hebiao
 * @version $Id:RequestProcessorThread.java, v0.1 2018/11/12 14:17 hebiao Exp $$
 */
public class RequestProcessorThread implements Callable<Boolean> {

    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {

        this.queue = queue;

    }

    @Override

    public Boolean call() throws Exception {

        Request request = queue.take();

        Map<Integer, Boolean> flagMap = RequestQueue.getInstance().getFlagMap();

        //不需要强制刷新的时候，查询请求去重处理

        if (!request.isForceFefresh()) {

            if (request instanceof InventoryUpdateDBRequest) {//如果是更新请求，那就置为false

                flagMap.put(request.getProductId(), true);

            } else {

                Boolean flag = flagMap.get(request.getProductId());

                /**
                 * 标志位为空，有三种情况
                 * 1、没有过更新请求
                 * 2、没有查询请求
                 * 3、数据库中根本没有数据
                 * 在最初情况，一旦库存了插入了数据，那就好会在缓存中也会放一份数据，
                 * 但这种情况下有可能由于redis中内存满了，redis通过LRU算法把这个商品给清除了，导致缓存中没有数据
                 * 所以当标志位为空的时候，需要从数据库重查询一次，并且把标志位置为false，以便后面的请求能够从缓存中取

                 */
                if (flag == null) {
                    flagMap.put(request.getProductId(), false);
                }

/**
 * 如果不为空，并且flag为true，说明之前有一次更新请求，说明缓存中没有数据了（更新缓存会先删除缓存），
 * 这个时候就要去刷新缓存，即从数据库中查询一次，并把标志位设置为false

 */

                if (flag != null && flag) {

                    flagMap.put(request.getProductId(), false);

                }

/**
 * 这种情况说明之前有一个查询请求，并且把数据刷新到了缓存中，所以这时候就不用去刷新缓存了，直接返回就可以了

 */
                if (flag != null && !flag) {

                    flagMap.put(request.getProductId(), false);

                    return true;

                }

            }

        }

        request.process();

        return true;

    }
}
