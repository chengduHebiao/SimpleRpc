/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer.domain;

import com.cn.netty.HttpServer.common.IdentityAnnotation;
import java.io.Serializable;
import java.util.Map;

/**
 * http请求对象
 *
 * @author hebiao
 * @version $Id:HRequest.java, v0.1 2019/1/29 17:06 hebiao Exp $$
 */
public class HRequest implements Serializable {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求类型
     */
    private METHOD method;

    /**
     * 客户端信息
     */
    @IdentityAnnotation(value = "User-Agent")
    private String userAgent;

    /**
     * host
     */
    @IdentityAnnotation(value = "Host")
    private String host;

    /**
     * cookie
     */
    @IdentityAnnotation(value = "Cookie")
    private String cookie;

    /**
     * cache
     */
    @IdentityAnnotation(value = "Cache-Control")
    private String cacheControl;


    @IdentityAnnotation(value = "Accept")
    private String accept;


    @IdentityAnnotation(value = "Accept-Encoding")
    private String acceptEncoding;


    @IdentityAnnotation(value = "Accept-Language")
    private String acceptLanguage;


    @IdentityAnnotation(value = "Connection")
    private String connection;
    /**
     * 请求参数
     */
    private Map<String, Object> body;

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public METHOD getMethod() {
        return method;
    }

    public void setMethod(METHOD method) {
        this.method = method;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HRequest{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", userAgent='" + userAgent + '\'' +
                ", host='" + host + '\'' +
                ", cookie='" + cookie + '\'' +
                ", cacheControl='" + cacheControl + '\'' +
                ", accept='" + accept + '\'' +
                ", acceptEncoding='" + acceptEncoding + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", connection='" + connection + '\'' +
                ", body=" + body +
                '}';
    }

    /**
     * http请求类型定义枚举
     */
    public enum METHOD {

        GET("GET"),

        PUT("put"),

        DELETE("delete"),

        POST("post");
        public String result;

        METHOD(String result) {

            this.result = result;
        }

    }
}
