/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

import java.util.Map;

/**
 * @author hebiao
 * @version $Id:TobaccoDataFetch.java, v0.1 2018/7/12 16:17 hebiao Exp $$
 */
public abstract class TobaccoDataFetch<R> implements FetchData<R, Map<String, String>> {

  @Override
  public R fetch(Map<String, String> stringStringMap) {
    return convert(stringStringMap);
  }

  abstract R convert(Map<String, String> object);
}
