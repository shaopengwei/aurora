package com.aurora.red.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 * 实现 restful 风格的接口服务
 * 为了防爬虫抓取需要考虑接口编码加密
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 11:28
 */
@RestController
@RequestMapping("/hello")
public class Hello {

  /**
   * say hello
   *
   * @return
   */
  @GetMapping("/hello")
  public String sayHello() {
    return "Hello";
  }

  /**
   * restful get 方法获取用户列表
   *
   * @return
   */
  @GetMapping(value = "/user")
  public String getUserList() {
    return "get user list.";
  }

  /**
   * restful get 根据 userId 获取用户信息
   *
   * @param userId
   * @return
   */
  @GetMapping(value = "/user/{userId}")
  public String getUserInfoById(@PathVariable("userId") int userId) {
    return "get user info by id. userId: " + userId;
  }

  /**
   * restful post 新增用户信息
   *
   * @param username
   * @param age
   * @return
   */
  @PostMapping(value = "/user")
  public String addUser(@RequestParam("username") String username, @RequestParam("age") int age) {
    return "post add user. username: " + username + ", age: " + age;
  }

  /**
   * restful delete 删除用户信息
   *
   * @param userId
   * @return
   */
  @DeleteMapping(value = "/user/{userId}")
  public String delUserById(@PathVariable("userId") int userId) {
    return "del user by id. userId: " + userId;
  }

  /**
   * restful put 更新用户信息
   *
   * @param userId
   * @return
   */
  @PutMapping(value = "/user/{userId}")
  public String updateUserInfoById(@PathVariable("userId") int userId) {
    return "update user by id. userId: " + userId;
  }

}
