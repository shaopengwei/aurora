package com.aurora.red.dao;

import com.aurora.red.dao.impl.SpiderTaskDao;
import com.aurora.red.entity.SpiderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-09 20:48
 */
@Repository
public class SpiderTaskDaoImpl implements SpiderTaskDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addTask(SpiderTask spiderTask) {
        return jdbcTemplate.update("insert into spider_task(task_name, area, task_type, status, create_time) " +
                        "values(?,?,?,?,?)", spiderTask.getTaskName(), spiderTask.getArea(), spiderTask.getTaskType(),
                spiderTask.getStatus(), spiderTask.getCreateTime());
    }

    @Override
    public List<SpiderTask> getAllTasks() {
        List<SpiderTask> spiderTasks = jdbcTemplate.query("select * from spider_task", new Object[]{},
                new BeanPropertyRowMapper<>(SpiderTask.class));
        return spiderTasks.size() > 0 ? spiderTasks : null;
    }

    @Override
    public SpiderTask getTaskInfoById(int taskId) {
        List<SpiderTask> spiderTasks = jdbcTemplate.query("select * from spider_task where task_id = ?",
                new Object[]{taskId}, new BeanPropertyRowMapper<>(SpiderTask.class));
        return spiderTasks.size() > 0 ? spiderTasks.get(0) : null;
    }

    @Override
    public List<SpiderTask> getTasksInfoByStatus(int status) {
        List<SpiderTask> spiderTasks = jdbcTemplate.query("select * from spider_task where status = ?",
                new Object[]{status}, new BeanPropertyRowMapper<>(SpiderTask.class));
        return spiderTasks.size() > 0 ? spiderTasks : null;
    }

    @Override
    public int delTaskById(int taskId) {
        return jdbcTemplate.update("delete from spider_task where task_id = ?", taskId);
    }

    @Override
    public int updateTaskStatusById(int taskId, int status) {
        return 0;
    }
}
