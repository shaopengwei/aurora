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

  public SSLContext getSslServerContext() throws Exception {
    // 密钥库 KeyStore
    KeyStore keyStore = KeyStore.getInstance("JKS");
    // 加载 keytool 生成的文件
    FileInputStream inputStream = new FileInputStream("E:\\JAVA\\work\\aurora\\cert\\server.jks");
    // 加载服务端的 KeyStore
    keyStore.load(inputStream, "Shao@1225".toCharArray());

    // 初始化密钥管理器
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    keyManagerFactory.init(keyStore, "Shao@1225".toCharArray());

    // 获取安全套接字协议（TLS 协议）的对象
    SSLContext sslContext = SSLContext.getInstance("TLS");
    // 初始化此上下文
    // 参数一：认证的密钥 参数二：对等信任认证 参数三：伪随机数生成器。由于单向认证，服务端不用验证客户端，所以第二个参数为 null
    sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

    inputStream.close();
    return sslContext;
  }

}
