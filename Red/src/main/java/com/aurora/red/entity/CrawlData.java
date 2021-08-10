package com.aurora.red.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-09 20:46
 */
@Data
@ToString
public class CrawlData {
    private int id;
    private int taskId;
    private String data;
    private String createTime;
}
