/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author hebiao
 * @version $Id:InitListener.java, v0.1 2018/11/12 14:20 hebiao Exp $$
 */
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override

    public void onApplicationEvent(ContextRefreshedEvent event) {


        if (event.getApplicationContext().getParent() != null) {

            return;

        }

        RequestProcessorThreadPool.init();

    }

}
