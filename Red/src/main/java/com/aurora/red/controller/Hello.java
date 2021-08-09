package com.aurora.red.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 11:28
 */
@RestController
@RequestMapping("/hello")
public class Hello {

  @GetMapping("hello")
  public String sayHello(){
    return "Hello";
  }
}
