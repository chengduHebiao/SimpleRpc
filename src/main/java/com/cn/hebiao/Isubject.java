
package com.cn.hebiao;

/**
 *
 *
 * @author hebiao
 * @version $Id:Isubject.java, v0.1 2018/9/6 11:20 hebiao Exp $$ 
 */
public interface Isubject {

  /**
   * 订阅主题
   * @param iobervser
   */
  void subscribe(Iobervser iobervser);


  /**
   * 取消订阅
   * @param iobervser
   */
  void deSubscribe(Iobervser iobervser);



}
