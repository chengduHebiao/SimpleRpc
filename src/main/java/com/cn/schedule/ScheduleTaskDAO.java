/**
 * Inc All Rights Reserved @2018
 */

package com.cn.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author hebiao
 * @version $Id:ScheduleTaskDAO.java, v0.1 2018/7/12 11:16 hebiao Exp $$
 */
public interface ScheduleTaskDAO extends JpaRepository<ScheduleTaskDO, Integer> {

    @Query(value = "SELECT t.* FROM schedule_task t WHERE t.status=2 AND t.locker=?1 limit 0,1 ", nativeQuery = true)
    ScheduleTaskDO selectLockedTask(String locker);

    @Query(value = "UPDATE schedule_task t SET t.status=2,t.locker=?1 WHERE t.status=1 AND t.next_exec_time<now() "
            + "limit 1", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    int lockTask(String locker);

    ScheduleTaskDO findByScheduleName(String scheduleName);

    @Query(value = "DELETE FROM schedule_task WHERE schedule_name like CONCAT(:scheduleName,'%') ", nativeQuery = true)
    @Modifying
    void deleteByScheduleNameLike(@Param("scheduleName") String scheduleName);

    @Query("UPDATE ScheduleTaskDO t SET t.status=1 WHERE t.status=2 AND SUBTIME(gmtModified,'00:00:05')<now()")
    @Modifying
    int unlockTasks();

    @Query(value = "UPDATE schedule_task t SET t.last_exec_time = now() WHERE t.id = ?1", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    int modifyLastExecTime(long id);

}
