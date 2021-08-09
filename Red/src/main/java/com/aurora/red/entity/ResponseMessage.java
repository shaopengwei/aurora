package com.aurora.red.entity;

import lombok.Data;

import java.util.Map;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 15:00
 */
@Data
public class ResponseMessage {
  public int errNo;
  public String errMessage;
  public Map<String, String> data;
}
