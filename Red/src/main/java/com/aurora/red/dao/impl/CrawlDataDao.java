package com.aurora.red.dao.impl;

import com.aurora.red.entity.CrawlData;

import java.util.List;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-09 20:48
 */
public interface CrawlDataDao {

    int addCrawlData(CrawlData crawlData);

    List<CrawlData> getCrawlDataByTaskId(int taskId);

    int delCrawlDataById(int id);
}
