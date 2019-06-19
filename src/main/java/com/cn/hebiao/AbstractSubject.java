package com.cn.hebiao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;

/**
 * @author hebiao
 * @version $Id:AbstractSubject.java, v0.1 2018/9/6 11:23 hebiao Exp $$
 */
public abstract class AbstractSubject implements Isubject {

    /**
     * 多个主题的观察者集合
     */
    protected static Map<String, List<Iobervser>> topicIobervers = new ConcurrentHashMap<>();
    protected String topic;

    public AbstractSubject(String topic) {
        this.topic = topic;
    }

    @Override
    public void subscribe(Iobervser iobervser) {
        if (iobervser == null) {
            return;
        }
        if (!StringUtils.isEmpty(topic)) {
            List<Iobervser> exits = topicIobervers.get(topic);
            if (exits == null || exits.isEmpty()) {
                exits = new ArrayList<>();
            }
            exits.add(iobervser);
            topicIobervers.put(topic, exits);
        }

    }

    @Override
    public void deSubscribe(Iobervser iobervser) {
        if (iobervser == null) {
            return;
        }
        if (!StringUtils.isEmpty(topic)) {
            List<Iobervser> exits = topicIobervers.get(topic);
            if (exits == null || exits.isEmpty()) {
                return;
            }
            exits.remove(iobervser);
            topicIobervers.put(topic, exits);
        }
    }

    /**
     * 通知订阅对象
     */
    public abstract void notifyObservers(Object args);
}
