/**
 * Inc All Rights Reserved @2018
 */

package com.cn.rpc.domain;

import java.io.Serializable;


/**
 * @author hebiao
 * @version $Id:RpcResponse.java, v0.1 2018/5/31 21:58 hebiao Exp $$
 */
public class RpcResponse implements Serializable {

    private String requestId;//请求id
    private Object result;//请求结果
    private Throwable error;//错误

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                ", error=" + error +
                '}';
    }
}
