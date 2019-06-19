package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:Handler.java, v0.1 2018/7/6 16:26 hebiao Exp $$
 */
public abstract class Handler implements IHandler {

    protected IHandler handler;//下一级的责任链

    protected void setHandler(IHandler handler) {
        this.handler = handler;
    }

    public abstract void handle(int requestId);
}
