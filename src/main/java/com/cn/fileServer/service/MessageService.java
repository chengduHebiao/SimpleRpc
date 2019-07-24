package com.cn.fileServer.service;

import com.cn.fileServer.Message;
import com.cn.fileServer.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hebiao
 * @version :MessageService.java, v0.1 2019/7/23 16:41 hebiao Exp $$
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public void insertMessage(Message message) {
        messageDao.save(message);

    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void not_supported(Message message) {
        messageDao.save(message);
        int k = 1/0;
    }
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory(Message message){
        messageDao.save(message);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nest(Message message) {
        messageDao.save(message);

    }
}
