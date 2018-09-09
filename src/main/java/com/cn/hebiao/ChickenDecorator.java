
package com.cn.hebiao;

/**
 *
 *
 * @author hebiao
 * @version $Id:ChickenDecorator.java, v0.1 2018/7/9 18:22 hebiao Exp $$ 
 */
public class ChickenDecorator extends Decorator {

  public ChickenDecorator(Compent compent) {
    super(compent);
  }

  @Override
  public void hanbao() {
    jiajirou();
    super.hanbao();
  }

  private void jiajirou() {
    System.out.println("加鸡肉");
  }
}
