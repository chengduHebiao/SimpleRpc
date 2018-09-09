
package com.cn.hebiao;

/**
 *
 *
 * @author hebiao
 * @version $Id:Observer.java, v0.1 2018/9/6 11:30 hebiao Exp $$
 */
public class Observer implements Iobervser {

  @Override
  public void update(Object args) {
    System.out.println(String.format("topic为%s的目标对象发生了改变",args.toString()));
  }
}
