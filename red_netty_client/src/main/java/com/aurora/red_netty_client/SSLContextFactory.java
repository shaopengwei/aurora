package com.aurora.red_netty_client;

import lombok.experimental.UtilityClass;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-12 23:42
 */
@UtilityClass
public class SSLContextFactory {

  public SSLContext getSslClientContext() throws Exception {
    KeyStore keyStore = KeyStore.getInstance("JKS");
    //加载客户端证书
    InputStream inputStream = new FileInputStream("E:\\JAVA\\work\\aurora\\cert\\client1.jks");
    keyStore.load(inputStream, "Shao@1225".toCharArray());
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init(keyStore);
    SSLContext sslContext = SSLContext.getInstance("TLS");
    //设置信任证书
    sslContext.init(null, trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(), null);

    inputStream.close();
    return sslContext;
  }
}
