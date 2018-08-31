/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.fileServer.service;

import com.cn.fileServer.FileDO;
import com.cn.fileServer.dao.FileRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hebiao
 * @version $Id:FileServiceImpl.java, v0.1 2018/8/29 17:25 hebiao Exp $$
 */
@Service
public class FileServiceImpl implements IFileService {

  @Autowired
  private FileRepository fileRepository;
  // 获得SpringBoot提供的mongodb的GridFS对象
  @Autowired
  private GridFsTemplate gridFsTemplate;

  @Override
  public FileDO saveFile(FileDO file) {
    return fileRepository.save(file);
  }

  @Override
  public void removeFile(String id) {
    fileRepository.delete(getFileById(id));
  }

  @Override
  public FileDO getFileById(String id) {
    System.out.println(id);
    return fileRepository.findById(id).get();
  }

  @Override
  public List<FileDO> listFilesByPage(int pageIndex, int pageSize) {
    return null;
  }

  @Override
  public ObjectId save(String path) {
    try {
      File file =new File(path);
      InputStream inputStream = new FileInputStream(file);
      return gridFsTemplate.store(inputStream,file.getName(),"image/png");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
