package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:ConcreatDecorator.java, v0.1 2018/7/9 18:25 hebiao Exp $$
 */
public class ConcreatDecorator implements Compent {

    public static void main(String[] args) {
        Compent compent = new ConcreatDecorator();
        Decorator decorator = new ChickenDecorator(compent);
        decorator.hanbao();
    }

    @Override
    public void hanbao() {
        System.out.println("汉堡");
    }
}
