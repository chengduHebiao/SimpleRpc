
package com.cn.hebiao.test;

import com.cn.fileServer.FileDO;
import com.cn.fileServer.service.IFileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * @author hebiao
 * @version $Id:MongoDbServiceTest.java, v0.1 2018/8/30 14:43 hebiao Exp $$
 */
public class MongoDbServiceTest extends BaseTest {

  @Autowired
  private IFileService fileService;
  @Resource
  private MongoDbFactory mongoDbFactory;

  @Test
  public void testSave() {

    String filePath = "F:\\接口文档 - BBD Finance.pdf";
    File f = new File(filePath);
    FileDO fileDO = new FileDO();
    fileDO.setId(String.valueOf(UUID.randomUUID()));
    fileDO.setFileName(filePath);
    fileDO.setUploadUser(1L);
    fileDO.setFileSize(f.length() + "");
    fileDO.setFileType(filePath.substring(filePath.lastIndexOf(".")));
    try {
      InputStream inputStream = new FileInputStream(new File(filePath));
      byte[] bytes = new byte[inputStream.available()];
      int len = 0;
      int temp;
      while ((temp = inputStream.read(bytes)) != -1) {
        bytes[len] = (byte) temp;
        len++;
      }
      fileDO.setFileContent(bytes);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    fileDO = fileService.saveFile(fileDO);

    System.out.println(fileDO.toString());

  }

  @Test
  public void testGet() {
    String id = "09c3ed24-e830-4cf2-ab3b-76e9b6849c69";
    FileDO file = fileService.getFileById(id);

    String filePath = "D:\\1.pdf";

    File fi = new File(filePath);
    try {

      FileOutputStream outStream = new FileOutputStream(fi);
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outStream);

      //FileWriter fileWriter = new FileWriter(filePath);
      //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      byte[] bytes = file.getFileContent();
      InputStream is = new ByteArrayInputStream(bytes);

      byte[] buff = new byte[1024];
      int len;
      while ((len = is.read(buff)) != -1) {
        bufferedOutputStream.write(buff, 0, len);
        bufferedOutputStream.flush();//使用缓冲的时候一定要flush
      }
      bufferedOutputStream.close();
      outStream.close();

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  @Test
  public void gridFSave() throws FileNotFoundException {
    String filePath = "F:\\接口文档-Finance.pdf";
    File file = new File(filePath);
    InputStream inputStream = new FileInputStream(file);
    DB db = mongoDbFactory.getLegacyDb();
    GridFS gridFS = new GridFS(db);
    String id = UUID.randomUUID().toString();
    System.out.println(id);
    DBObject query = new BasicDBObject("_id", id);
    GridFSDBFile gridFSDBFile = gridFS.findOne(query);
    if (gridFSDBFile == null) {
      GridFSInputFile gridFSInputFile = gridFS.createFile(inputStream);
      gridFSInputFile.setId(id);
      gridFSInputFile.setFilename(file.getName());
      gridFSInputFile.save();
    }

  }
}
