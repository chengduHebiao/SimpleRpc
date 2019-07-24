package com.cn.fileServer.dao;

import com.cn.fileServer.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hebiao
 * @version :UserDao.java, v0.1 2019/7/23 14:13 hebiao Exp $$
 */
public interface UserDao extends JpaRepository<User,Long> {

}
