package com.cn.fileServer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hebiao
 * @version :Message.java, v0.1 2019/7/23 16:36 hebiao Exp $$
 */
@Entity
@Table(name = "message" )
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Message( String topic) {
        this.id = id;
        this.topic = topic;
    }
}
