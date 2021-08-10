package com.aurora.red;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 11:24
 */
@SpringBootApplication
@ServletComponentScan(value = "com.aurora.red.filter")
public class Index {
  public static void main(String[] args) {
    SpringApplication.run(Index.class, args);
  }
}
