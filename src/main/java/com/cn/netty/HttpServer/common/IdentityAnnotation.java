/**
 * BBD Service Inc All Rights Reserved @2018
 */
package com.cn.netty.HttpServer.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hebiao
 * @version $Id:IdentityAnnotation.java, v0.1 2019/1/29 18:16 hebiao Exp $$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IdentityAnnotation {

    String value();
}
