/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.hebiao;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hebiao
 * @version $Id:RealFetch.java, v0.1 2018/7/12 16:24 hebiao Exp $$
 */
@Component
public class RealFetch extends TobaccoDataFetch<String> {

  private Logger logger = LoggerFactory.getLogger(RealFetch.class);

  @Override
  String convert(Map<String, String> object) {
    return "HEBIAO";
  }

  @PostConstruct
  public void init() {
    logger.warn(this.fetch(new HashMap<>()));
  }
}
