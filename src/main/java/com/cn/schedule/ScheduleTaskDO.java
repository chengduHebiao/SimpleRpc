/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.util.StringUtils;

/**
 *
 *
 * @author hebiao
 * @version $Id:ScheduleTaskDO.java, v0.1 2018/7/12 11:13 hebiao Exp $$ 
 */
@Entity
@Table(name = "schedule_task")
public class ScheduleTaskDO implements Serializable{

  private static final long serialVersionUID = 5652213124498939276L;

  public static final int STATUS_PAUSED = 0;
  public static final int STATUS_WAITING = 1;
  public static final int STATUS_EXECUTING = 2;
  public static final int STATUS_FINISHED = 3;
  //主键ID
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String scheduleName;
  //beanName
  private String beanName;
  //任务的参数
  private String taskParameter;
  //下次执行时间
  private LocalDateTime nextExecTime;
  //执行计划
  private String execPlan;
  //上次执行时间
  private LocalDateTime lastExecTime;
  //加锁者
  private String locker;
  //状态 1:待执行 2:执行中 3:已完成
  private Integer status;
  //错误信息
  private String errorMsg;
  //记录创建时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(updatable = false, insertable = false)
  private LocalDateTime gmtCreate;

  //记录修改时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(updatable = false, insertable = false)
  private LocalDateTime gmtModified;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getNextExecTime() {
    return nextExecTime;
  }

  public void setNextExecTime(LocalDateTime nextExecTime) {
    this.nextExecTime = nextExecTime;
  }

  public String getExecPlan() {
    return execPlan;
  }

  public void setExecPlan(String execPlan) {
    this.execPlan = execPlan;
  }

  public LocalDateTime getLastExecTime() {
    return lastExecTime;
  }

  public void setLastExecTime(LocalDateTime lastExecTime) {
    this.lastExecTime = lastExecTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
    if (!StringUtils.isEmpty(errorMsg) && errorMsg.length() > 100) {
      this.errorMsg = errorMsg.substring(0, 50);
    }
  }

  public LocalDateTime getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(LocalDateTime gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public LocalDateTime getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(LocalDateTime gmtModified) {
    this.gmtModified = gmtModified;
  }

  public String getBeanName() {
    return beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getTaskParameter() {
    return taskParameter;
  }

  public void setTaskParameter(String taskParameter) {
    this.taskParameter = taskParameter;
  }

  public String getScheduleName() {
    return scheduleName;
  }

  public void setScheduleName(String scheduleName) {
    this.scheduleName = scheduleName;
  }

  public String getLocker() {
    return locker;
  }

  public void setLocker(String locker) {
    this.locker = locker;
  }

}
