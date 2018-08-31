/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.rpc.annotation;

import com.cn.rpc.interfaces.HelloServiceImpl;
import java.lang.reflect.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author hebiao
 * @version $Id:RpcBeanPostProcessor.java, v0.1 2018/8/24 15:38 hebiao Exp $$
 */
@Component
public class RpcBeanPostProcessor implements BeanPostProcessor {

  @Rpc
  @Autowired
  private HelloServiceImpl helloService;


  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {

    Class<?> clazz = bean.getClass();
    Field[] fields = clazz.getFields();
    for (Field field : fields) {
      Rpc rpc = field.getAnnotation(Rpc.class);
      if (rpc != null) {
        System.out.println("rpc class-->" + bean.getClass().getName());
      }
    }
    return bean;
  }

}
