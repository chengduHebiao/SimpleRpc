
package com.cn.fileServer.service;

import com.cn.fileServer.FileDO;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * @author hebiao
 * @version $Id:IFileService.java, v0.1 2018/8/29 17:24 hebiao Exp $$
 */
public interface IFileService {

  /**
   * 保存文件
   */
  FileDO saveFile(FileDO file);

  /**
   * 删除文件
   */
  void removeFile(String id);

  /**
   * 根据id获取文件
   */
  FileDO getFileById(String id);

  /**
   * 分页查询，按上传时间降序
   */
  List<FileDO> listFilesByPage(int pageIndex, int pageSize);



  ObjectId save(String filePath);
}
