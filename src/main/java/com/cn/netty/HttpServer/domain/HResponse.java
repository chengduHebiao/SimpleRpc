/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * http 响应对象
 *
 * @author hebiao
 * @version $Id:HResponse.java, v0.1 2019/1/29 17:06 hebiao Exp $$
 */
public class HResponse implements Serializable {

    /**
     * 協議版本
     */
    private String version;

    /**
     * 狀態碼
     */
    private CODE status;

    /**
     * 时间
     */
    private Date date;

    /**
     * 类型
     */
    private String contentType;

    private String Connection;

    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CODE getStatus() {
        return status;
    }

    public void setStatus(CODE status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getConnection() {
        return Connection;
    }

    public void setConnection(String connection) {
        Connection = connection;
    }

    @Override
    public String toString() {
        return "HResponse{" +
                "version='" + version + '\'' +
                ", status=" + status +
                ", date=" + date +
                ", contentType='" + contentType + '\'' +
                ", Connection='" + Connection + '\'' +
                ", server='" + server + '\'' +
                '}';
    }

    public enum CODE {

        OK(200),
        UNAUTHORIZED(401),
        NOT_FOUND(404),
        FORBIDDEN(403),
        BAD_GETWAY(502);

        Integer code;

        CODE(Integer code) {
            this.code = code;
        }

    }
}
