/**
 * Inc All Rights Reserved @2018
 */

package com.cn.schedule;

/**
 * 基于数据库的分布式调度框架
 *
 * @author hebiao
 * @version $Id:ScheduleService.java, v0.1 2018/7/12 11:15 hebiao Exp $$
 */
public interface ScheduleService {

    /**
     * 添加执行计划
     *
     * @param scheduleName 计划名称
     * @param beanName bean的名称，在执行的时候会调用该bean的execute方法。该bean必须实现Task接口。
     * @param parameter 执行的参数
     * @param schedulePlan 执行计划，只支持cron表达式
     * @return 执行计划的ID
     */
    int addSchedule(String scheduleName, String beanName, String parameter, String schedulePlan);

    /**
     * 根据计划名称查询计划的详情
     *
     * @param scheduleName 计划名称
     * @return 计划的详情
     */
    ScheduleTaskDO findByScheduleName(String scheduleName);

    /**
     * 暂停该执行计划
     *
     * @param scheduleId 执行计划ID
     */
    void pauseSchedule(int scheduleId);

    /**
     * 删除该执行计划
     *
     * @param scheduleId 执行计划ID
     */
    void deleteSchedule(int scheduleId);

    /**
     * 启动改执行计划，和暂停配合使用。新增的执行计划，默认是启动状态
     */
    void startSchedule(int scheduleId);

    /**
     * 根据计划名模糊（后置模糊）匹配完成删除
     *
     * @param scheduleName 计划名
     */
    void deleteByScheduleNameLike(String scheduleName);

}
