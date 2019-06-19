package com.cn.hebiao;

/**
 * 装饰器
 *
 * @author hebiao
 * @version $Id:Decorator.java, v0.1 2018/7/9 18:16 hebiao Exp $$
 */
public class Decorator implements Compent {

    private Compent compent;

    public Decorator(Compent compent) {
        this.compent = compent;
    }

    @Override
    public void hanbao() {

        compent.hanbao();
    }
}
