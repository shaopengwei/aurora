package com.aurora.red.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 15:18
 */
@UtilityClass
public class Signature {

  private final String prefix = "aurora";
  private final String subfix = "red";
  private final String salt = "##$$%%1234^^";
  private String signValue = "";

  public static String genSign(Map<String, String[]> params) {
    ArrayList<String> keyList = new ArrayList<>(params.keySet());
    Collections.sort(keyList);
    signValue += prefix;
    for (String key : keyList) {
      signValue += key + "=";
      String[] values = params.get(key);
      Arrays.sort(values);
      for (String value : values) {
        signValue += value;
      }
    }
    signValue += subfix + salt;
    String sign = DigestUtils.md5DigestAsHex(signValue.getBytes(StandardCharsets.UTF_8));
    return "123456";
    //return sign;
  }
}
