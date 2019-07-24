package com.cn.hebiao.test;

import com.cn.fileServer.User;
import com.cn.fileServer.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hebiao
 * @version :TransactionalService.java, v0.1 2019/7/23 16:53 hebiao Exp $$
 */
public class TransactionalService extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void transactional() {
        User user = new User("nest" + System.currentTimeMillis());
        userService.nest(user);
    }
}
