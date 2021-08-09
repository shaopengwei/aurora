package com.aurora.red.controller;

import com.aurora.red.entity.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 14:45
 */
@RestController
@RequestMapping(value = "/error")
public class ErrorController {

  @RequestMapping(value = "/signerror")
  public ResponseMessage signFilterError(){
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setErrNo(10000);
    responseMessage.setErrMessage("sign error.");
    return responseMessage;
  }

  @RequestMapping(value = "/paramerror")
  public ResponseMessage paramError(){
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setErrNo(10000);
    responseMessage.setErrMessage("param error.");
    return responseMessage;
  }
}
