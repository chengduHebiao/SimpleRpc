/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.schedule;

/**
 * @author hebiao
 * @version $Id:Task.java, v0.1 2018/7/12 11:15 hebiao Exp $$
 */
public interface Task {

  /**
   * 任务的执行逻辑
   */
  void execute(String taskParameter);
}
