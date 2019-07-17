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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hebiao
 * @version $Id:FileController.java, v0.1 2018/8/30 17:25 hebiao Exp $$
 */

public class FileController {


    @Autowired
    private IFileService fileService;

    @Resource
    private MongoDbFactory mongoDbFactory;
    @Resource
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Bean
    public GridFSBucket getGridFSBuckets() {
        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }

    @GetMapping(value = "/{id}")
    public void getImageById(@PathVariable String id, HttpServletResponse response) throws IOException {
        // 查询单个文件

        DB db = mongoDbFactory.getLegacyDb();
        GridFS gridFS = new GridFS(db);
        DBObject querys = new BasicDBObject("_id", id);
        GridFSDBFile gfsfile = gridFS.findOne(querys);

        if (gfsfile == null) {
            return;
        }
        String fileName = gfsfile.getFilename().replace(",", "");
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        // 通知浏览器进行文件下载
        response.setContentType(gfsfile.getContentType());
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        gfsfile.writeTo(response.getOutputStream());
    }


    public GridFsResource convertGridFSFile2Resource(GridFSFile gridFsFile) {
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
        return new GridFsResource(gridFsFile, gridFSDownloadStream);
    }
}
