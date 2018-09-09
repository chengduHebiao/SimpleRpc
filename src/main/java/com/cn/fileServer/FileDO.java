
package com.cn.fileServer;

import java.util.Arrays;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hebiao
 * @version $Id:FileDO.java, v0.1 2018/8/29 16:59 hebiao Exp $$
 */
@Document
public class FileDO {

  private String id;
  //文件名称
  private String fileName;
  //上传者
  private Long uploadUser;
  //文件大小
  private String fileSize;
  //文件类型
  private String fileType;
  //文件内容
  private byte[] fileContent;
  //文件路径
  private String filePath;

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Long getUploadUser() {
    return uploadUser;
  }

  public void setUploadUser(Long uploadUser) {
    this.uploadUser = uploadUser;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public byte[] getFileContent() {
    return fileContent;
  }

  public void setFileContent(byte[] fileContent) {
    this.fileContent = fileContent;
  }

  @Override
  public String toString() {
    return "FileAttachmentDO{" +
        "fileName='" + fileName + '\'' +
        ", uploadUser=" + uploadUser +
        ", fileSize=" + fileSize +
        ", fileType='" + fileType + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileDO that = (FileDO) o;
    return Objects.equals(this.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(fileName, uploadUser, fileSize, fileType);
    result = 31 * result + Arrays.hashCode(fileContent);
    return result;
  }
}
