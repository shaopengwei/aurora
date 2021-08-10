package com.aurora.red_proxy.server;

import com.aurora.red_proxy.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * TODO
 * 丢弃任何进入的数据，启动服务端的 DiscardServerHandler
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/10 11:43
 */
public class DiscardServer {

  private int port;

  public DiscardServer(int port) {
    super();
    this.port = port;
  }

  public void run() throws Exception {

    /***
     * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
     * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。 在这个例子中我们实现了一个服务端的应用，
     * 因此会有2个NioEventLoopGroup会被使用。
     *  第一个经常被叫做‘boss’，用来接收进来的连接
     *  第二个经常被叫做‘worker’，用来处理已经被接收的连接
     * 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
     * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
     * 并且可以通过构造函数来配置他们的关系。
     */
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    System.out.println("准备运行端口：" + port);
    try {
      /**
       * Bootstrap 意思是引导，一个 Netty 应用通常由一个 Bootstrap 开始，主要作用是配置整个 Netty 程序，串联各个组件，
       * Netty 中 Bootstrap 类是客户端程序的启动引导类，ServerBootstrap 是服务端启动引导类。
       *
       * ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
       */
      ServerBootstrap b = new ServerBootstrap();
      /**
       * 这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not set异常
       */
      b = b.group(bossGroup, workerGroup);
      /**
       * Channel 是 Netty 网络通信的组件，能够用于执行网络 I/O 操作。Channel 为用户提供：
       * 1.当前网络连接的通道的状态（例如是否打开？是否已连接？）
       * 2.网络连接的配置参数 （例如接收缓冲区大小）
       * 3.提供异步的网络 I/O 操作(如建立连接，读写，绑定端口)，异步调用意味着任何 I/O 调用都将立即返回，
       * 并且不保证在调用结束时所请求的 I/O 操作已完成。调用立即返回一个 ChannelFuture 实例，
       * 通过注册监听器到 ChannelFuture 上，可以 I/O 操作成功、失败或取消时回调通知调用方。
       * 4. 支持关联 I/O 操作与对应的处理程序。
       * 5. 不同协议、不同的阻塞类型的连接都有不同的 Channel 类型与之对应。
       *
       * 下面是一些常用的 Channel 类型：
       * NioSocketChannel，异步的客户端 TCP Socket 连接。
       * NioServerSocketChannel，异步的服务器端 TCP Socket 连接。
       * NioDatagramChannel，异步的 UDP 连接。
       * NioSctpChannel，异步的客户端 Sctp 连接。
       * NioSctpServerChannel，异步的 Sctp 服务器端连接，这些通道涵盖了 UDP 和 TCP 网络 IO 以及文件 IO。
       *
       * Netty 基于 Selector 对象实现 I/O 多路复用，通过 Selector 一个线程可以监听多个连接的 Channel 事件。
       *
       * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接
       * 这里告诉Channel如何获取新的连接.
       */
      b = b.channel(NioServerSocketChannel.class);
      /**
       * 设置连接配置参数
       *
       * option() 是提供给 NioServerSocketChannel 用来接收进来的连接。
       */
      b = b.option(ChannelOption.SO_BACKLOG, 128);
      /**
       * childOption() 是提供给由父管道 ServerChannel 接收到的连接，
       * 在这个例子中也是NioServerSocketChannel。
       */
      b = b.childOption(ChannelOption.SO_KEEPALIVE, true);
      /**
       * 配置入站、出站事件 handler
       */
      b = b.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
          // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
          ch.pipeline().addLast(new HttpResponseEncoder());
          // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
          ch.pipeline().addLast(new HttpRequestDecoder());
          // 增加自定义实现的Handler
          // ch.pipeline().addLast(new DiscardServerHandler());// demo1.discard
          // ch.pipeline().addLast(new ResponseServerHandler());//demo2.echo
          // ch.pipeline().addLast(new TimeServerHandler());//demo3.time
        }
      });
      /**
       * 在 Netty 中所有的 IO 操作都是异步的，不能立刻得知消息是否被正确处理。
       * 但是可以过一会等它执行完成或者直接注册一个监听，具体的实现就是通过 Future 和 ChannelFutures，
       * 他们可以注册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件。
       *
       * 绑定端口并启动去接收进来的连接
       */
      ChannelFuture f = b.bind(port).sync();
      /**
       * 这里会一直等待，直到socket被关闭
       */
      f.channel().closeFuture().sync();
    } finally {
      /**
       * 关闭
       */
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }
}
