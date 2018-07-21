/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.schedule;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author hebiao
 * @version $Id:ScheduleServiceImpl.java, v0.1 2018/7/12 11:17 hebiao Exp $$
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

  @Value("${scheduler.thread.num:20}")
  private int threadNum = 20;
  @Autowired(required = false)
  private Map<String, Task> taskMap;
  private ExecutorService executorService;
  private boolean destroyed = false;

  @Autowired
  private ScheduleTaskDAO scheduleTaskDAO;

  @Override
  public int addSchedule(String scheduleName, String beanName, String taskParameter,
      String schedulePlan) {
    ScheduleTaskDO scheduleTaskDO = new ScheduleTaskDO();
    scheduleTaskDO.setScheduleName(scheduleName);
    scheduleTaskDO.setBeanName(beanName);
    scheduleTaskDO.setTaskParameter(taskParameter);
    scheduleTaskDO.setStatus(ScheduleTaskDO.STATUS_WAITING);
    scheduleTaskDO.setExecPlan(schedulePlan);
    scheduleTaskDO.setNextExecTime(getNextExecTime(schedulePlan));
    if (null == scheduleTaskDO.getNextExecTime()) {
      throw new RuntimeException("无法添加schedule:" + schedulePlan);
    }
    scheduleTaskDAO.save(scheduleTaskDO);
    return scheduleTaskDO.getId();
  }

  @Override
  public ScheduleTaskDO findByScheduleName(String scheduleName) {
    return scheduleTaskDAO.findByScheduleName(scheduleName);
  }

  @Override
  public void pauseSchedule(int scheduleId) {
    ScheduleTaskDO scheduleTaskDO = scheduleTaskDAO.getOne(scheduleId);
    if (null == scheduleTaskDO) {
      return;
    }
    scheduleTaskDO.setStatus(ScheduleTaskDO.STATUS_PAUSED);
    scheduleTaskDAO.save(scheduleTaskDO);
  }

  @Override
  public void deleteSchedule(int scheduleId) {
    scheduleTaskDAO.deleteById(scheduleId);
  }

  @Override
  public void deleteByScheduleNameLike(String scheduleName) {
    scheduleTaskDAO.deleteByScheduleNameLike(scheduleName);
  }

  @Override
  public void startSchedule(int scheduleId) {
    ScheduleTaskDO scheduleTaskDO = scheduleTaskDAO.getOne(scheduleId);
    scheduleTaskDO.setStatus(ScheduleTaskDO.STATUS_WAITING);
    scheduleTaskDAO.save(scheduleTaskDO);
  }

  @PostConstruct
  public void init() {
    startUnlockTaskThread();
    executorService = Executors.newFixedThreadPool(threadNum,
        new ThreadFactoryBuilder().setNameFormat("pool-schedule-%d").build());
    new Thread(new ScheduleThreadTask(), "schedule-scanner").start();
  }

  /**
   * 任务加锁之后，有可能因为某些原因无法及时释放，该线程负责定时扫描未及时释放的任务，进行释放操作
   */
  private void startUnlockTaskThread() {
    new Thread(() -> {
      while (true) {
        int affectedRows = scheduleTaskDAO.unlockTasks();
        logger.warn("scheduler unlocked {} tasks", affectedRows);
        try {
          TimeUnit.MINUTES.sleep(5);
        } catch (InterruptedException e) {
          logger.error(e.getMessage(), e);
          throw new RuntimeException(e.getMessage(), e);
        }
      }
    }).start();
  }


  @PreDestroy
  public void destroy() {
    destroyed = true;
  }

  private LocalDateTime getNextExecTime(String plan) {
    try {
      CronExpression cronExpression = new CronExpression(plan);
      Date nextValidTimeAfter = cronExpression.getNextValidTimeAfter(new Date());
      if (null == nextValidTimeAfter) {
        return null;
      }
      return LocalDateTime.ofInstant(nextValidTimeAfter.toInstant(), ZoneId.systemDefault());
    } catch (ParseException e) {
      logger.warn(e.getMessage(), e);
      return null;
    }
  }

  private void executeTask(ScheduleTaskDO scheduleTaskDO) {
    String beanName = scheduleTaskDO.getBeanName();
    Task task = taskMap.get(beanName);
    if (null == task) {
      logger.error("can not find bean by name:" + beanName);
      return;
    }
    try {
      scheduleTaskDAO.modifyLastExecTime(scheduleTaskDO.getId());
      logger.info("开始执行任务:{}", scheduleTaskDO.getTaskParameter());
      task.execute(scheduleTaskDO.getTaskParameter());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      scheduleTaskDO.setErrorMsg(e.getMessage());
      scheduleTaskDAO.save(scheduleTaskDO);
    }

  }

  private class ScheduleThreadTask implements Runnable {

    @Override
    public void run() {
      String locker = HostUtils.getServerIp() + "-" + HostUtils.getPid() + "_" + Thread.currentThread().getId();
      while (!destroyed) {
        try {
          int affectedRows = scheduleTaskDAO.lockTask(locker);
          if (affectedRows <= 0) {
            TimeUnit.SECONDS.sleep(5);
            continue;
          }
          ScheduleTaskDO scheduleTaskDO = scheduleTaskDAO.selectLockedTask(locker);
          // 可能在锁住和获取 这段时间内，定时任务已经被删除
          if (null == scheduleTaskDO) {
            logger.info("task has been delete,continue lock task");
            continue;
          }
          scheduleTaskDO.setNextExecTime(getNextExecTime(scheduleTaskDO.getExecPlan()));
          if (null == scheduleTaskDO.getNextExecTime()) {
            scheduleTaskDO.setStatus(ScheduleTaskDO.STATUS_FINISHED);
          } else {
            scheduleTaskDO.setStatus(ScheduleTaskDO.STATUS_WAITING);
          }
          scheduleTaskDAO.save(scheduleTaskDO);
          logger.warn("提交执行任务到线程池,scheduleId: {}", scheduleTaskDO.getTaskParameter());
          executorService.submit(() -> executeTask(scheduleTaskDO));
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
      logger.warn("stopped schedule thread");
    }


  }

}
