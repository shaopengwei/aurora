package com.aurora.red_netty_server.util;

import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/12 18:12
 */
@UtilityClass
public class SSLContextFactory {

  public static SSLContext getSslContext() throws Exception {
    char[] passArray = "Shao@1225".toCharArray();
    SSLContext sslContext = SSLContext.getInstance("TLSv1");
    KeyStore ks = KeyStore.getInstance("PKCS12");
    //加载 keytool 生成的文件
    FileInputStream inputStream = new FileInputStream("/Users/shaopengwei/tmp/cert/server.jks");
    ks.load(inputStream, passArray);
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    kmf.init(ks, passArray);
    sslContext.init(kmf.getKeyManagers(), null, null);
    inputStream.close();
    return sslContext;
  }
}
