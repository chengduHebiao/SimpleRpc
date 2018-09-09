
package com.cn.fileServer.dao;

import com.cn.fileServer.FileDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author hebiao
 * @version $Id:FileRepository.java, v0.1 2018/8/29 17:22 hebiao Exp $$
 */
@Repository
public interface FileRepository extends MongoRepository<FileDO,String> {

}
