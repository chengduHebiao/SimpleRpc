/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

/**
 * 请求接口
 *
 * @author hebiao
 * @version $Id:Request.java, v0.1 2018/11/12 14:09 hebiao Exp $$
 */
public interface Request {

    void process();

    Integer getProductId();

    boolean isForceFefresh();
}
