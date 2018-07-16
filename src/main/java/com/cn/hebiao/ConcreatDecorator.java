/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

/**
 *
 *
 * @author hebiao
 * @version $Id:ConcreatDecorator.java, v0.1 2018/7/9 18:25 hebiao Exp $$
 */
public class ConcreatDecorator implements Compent {

  @Override
  public void hanbao() {
    System.out.println("汉堡");
  }

  public static void main(String[] args) {
    Compent compent = new ConcreatDecorator();
    Decorator decorator = new ChickenDecorator(compent);
    decorator.hanbao();
  }
}
