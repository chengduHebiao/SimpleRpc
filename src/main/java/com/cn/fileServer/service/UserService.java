package com.cn.fileServer.service;

import com.cn.fileServer.Message;
import com.cn.fileServer.User;
import com.cn.fileServer.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务回滚测试
 * 假设有类A方法methodB,有类B的方法methodB()
 *
 * @author hebiao
 * @version :UserService.java, v0.1 2019/7/23 16:40 hebiao Exp $$
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageService messageService;


    /**
     *PROPAGATION_REQUIRED  默认的传播级别
     * A.methodA()调用B的methodB()方法，那么如果A的方法包含事务，则B的方法则不重新开启事务，
     *
     *1、  如果B的methodB()抛出异常，A的methodB()没有捕获，则A和B的事务都会回滚；
     *
     * 2、   如果B的methodB()运行期间异常会导致B的methodB()的回滚，A如果捕获了异常，并正常提交事务，则会发生Transaction rolled back because it has been
     * marked as rollback-only的异常。
     *
     * 3、  如果A的methodA()运行期间异常，则A和B的Method的事务都会被回滚
     *
     * @param user
     */
    @Transactional
    public void insertUser(User user) {

        userDao.save(user);
        Message message = new Message("test" + System.currentTimeMillis());
        try{
            messageService.insertMessage(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        int i = 1 / 0;

    }

    /**
     * B的事务为PROPAGATION_NOT_SUPPORTS
     * A有事务，B方法抛出异常，A 捕获，都不回滚
     * A有事务,B方法抛出异常，A不捕获，B不回滚，A回滚
     *
     * @param user
     */
    @Transactional
    public void not_supports(User user) {

        userDao.save(user);
        Message message = new Message("test" + System.currentTimeMillis());
        messageService.not_supported(message);

    }

    public void mandatory(User user){
        userDao.save(user);
        Message message = new Message("test" + System.currentTimeMillis());
        messageService.mandatory(message);

    }

    /**
     * nest 在Hibernate中不被支持
     *
     * 1、        如果A的MethodA()不存在事务，则B的methodB()运行在一个新的事务中，B.method()抛出的异常，B.methodB()回滚,但A.methodA()不回滚；如果A.methoda()抛出异常，则A.methodA()和B.methodB()操作都不回滚。
     *
     * 2、        如果A的methodA()存在事务，则A的methoda()抛出异常，则A的methoda()和B的Methodb()都会被回滚；
     *
     * 3、        如果A的MethodA()存在事务，则B的methodB()抛出异常，B.methodB()回滚，如果A不捕获异常，则A.methodA()和B.methodB()都会回滚，如果A捕获异常，则B.methodB()回滚,A不回滚；
     *
     * @param user
     */
    public void nest(User user){
        userDao.save(user);
        Message message = new Message("nest"+System.currentTimeMillis());
        messageService.nest(message);
        int k=1/0;
    }
}
