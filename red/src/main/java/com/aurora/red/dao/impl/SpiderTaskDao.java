package com.aurora.red.dao.impl;

import com.aurora.red.entity.SpiderTask;

import java.util.List;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-09 20:49
 */
public interface SpiderTaskDao {

    int addTask(SpiderTask spiderTask);

    List<SpiderTask> getAllTasks();

    SpiderTask getTaskInfoById(int taskId);

    List<SpiderTask> getTasksInfoByStatus(int status);

    int delTaskById(int taskId);

    int updateTaskStatusById(int taskId, int status);
}
