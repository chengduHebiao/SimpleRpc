/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.controller;

import com.cn.fileServer.service.IFileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hebiao
 * @version $Id:FileController.java, v0.1 2018/8/30 17:25 hebiao Exp $$
 */
@RestController
public class FileController {


  @Autowired
  private IFileService fileService;

  @Resource
  private MongoDbFactory mongoDbFactory;

  @Bean
  public GridFSBucket getGridFSBuckets() {
    MongoDatabase db = mongoDbFactory.getDb();
    return GridFSBuckets.create(db);
  }

  @Resource
  private GridFSBucket gridFSBucket;
  @Autowired
  private GridFsTemplate gridFsTemplate;

  @GetMapping(value = "/{id}")
  public void getImageById(@PathVariable String id, HttpServletResponse response) throws IOException {
    // 查询单个文件
    /*Query query = Query.query(Criteria.where("id").is(id));
    GridFSFile gfsfile = gridFsTemplate.findOne(query);*/

    DB db = mongoDbFactory.getLegacyDb();
    GridFS gridFS = new GridFS(db);
    DBObject querys = new BasicDBObject("_id", id);
    GridFSDBFile gfsfile = gridFS.findOne(querys);

    if (gfsfile == null) {
      return;
    }
    //GridFsResource gridFsResource = convertGridFSFile2Resource(gfsfile);
    String fileName = gfsfile.getFilename().replace(",", "");
    fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
    // 通知浏览器进行文件下载
    response.setContentType(gfsfile.getContentType());
    response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
    /*  IOUtils.copy(gridFsResource.getInputStream(),response.getOutputStream());*/
    gfsfile.writeTo(response.getOutputStream());


  }


  public GridFsResource convertGridFSFile2Resource(GridFSFile gridFsFile) {
    GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
    return new GridFsResource(gridFsFile, gridFSDownloadStream);
  }
}
