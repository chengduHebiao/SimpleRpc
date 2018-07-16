/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:FetchData.java, v0.1 2018/7/12 16:16 hebiao Exp $$
 */
public interface FetchData<R, T> {

  R fetch(T t);
}
