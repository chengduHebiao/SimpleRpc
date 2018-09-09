

package com.cn.hebiao;

/**
 * 函数式接口
 * @author hebiao
 * @version $Id:Converter.java, v0.1 2018/9/3 11:27 hebiao Exp $$
 */
@FunctionalInterface
public interface Converter<R, T> {

  R convert(T t);
}
