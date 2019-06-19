package com.cn.hebiao.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hebiao
 * @version $Id:BaseTest.java, v0.1 2018/8/30 14:40 hebiao Exp $$
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @Before
    public void init() {
        LOG.info("测试开始--------");
    }

    @After
    public void after() {
        LOG.info("测试结束---------");
    }
}
