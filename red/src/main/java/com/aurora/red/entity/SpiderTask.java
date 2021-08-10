package com.aurora.red.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-09 20:47
 */
@Data
@ToString
public class SpiderTask {
    private int taskId;
    private String taskName;
    private String area;        // 地域信息, 0:绥化北林; 1:哈尔滨巴彦; 2:哈尔滨延寿;
    private String taskType;    // 0:饿了么; 1:美团;
    private int status;         // 0:创建; 1:执行中; 2:执行成功; 3:执行失败; 4:废弃;
    private String createTime;
    private String startTime;
    private String endTime;
}
