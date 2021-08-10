package com.aurora.red_proxy.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/10 14:35
 */
public class HttpProxyServerHandler extends ChannelHandlerAdapter {

  private ChannelFuture cf;
  private String host;
  private int port;

  @Override
  public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof FullHttpRequest) {
      FullHttpRequest request = (FullHttpRequest) msg;
      String host = (String) request.headers().get("host");
      String[] temp = host.split(":");
      int port = 80;
      if (temp.length > 1) {
        port = Integer.parseInt(temp[1]);
      } else {
        if (request.uri().indexOf("https") == 0) {
          port = 443;
        }
      }
      this.host = temp[0];
      this.port = port;
      if ("CONNECT".equalsIgnoreCase(String.valueOf(request.method().name()))) {
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ctx.writeAndFlush(response);
        return;
      }
      //连接至目标服务器
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(ctx.channel().eventLoop()) // 注册线程池
          .channel(ctx.channel().getClass()) // 使用NioSocketChannel来作为连接用的channel类
          .handler(new HttpProxyInitializer(ctx.channel()));

      ChannelFuture cf = bootstrap.connect(this.host, this.port);
      cf.addListener(new ChannelFutureListener() {
        public void operationComplete(ChannelFuture future) throws Exception {
          if (future.isSuccess()) {
            future.channel().writeAndFlush(msg);
          } else {
            ctx.channel().close();
          }
        }
      });
//            ChannelFuture cf = bootstrap.connect(temp[0], port).sync();
//            cf.channel().writeAndFlush(request);
    } else { // https 只转发数据，不做处理
      if (cf == null) {
        //连接至目标服务器
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(ctx.channel().eventLoop()) // 复用客户端连接线程池
            .channel(ctx.channel().getClass()) // 使用NioSocketChannel来作为连接用的channel类
            .handler(new ChannelInitializer() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new ChannelHandlerAdapter() {
                  @Override
                  public void channelRead(ChannelHandlerContext ctx0, Object msg) throws Exception {
                    ctx.channel().writeAndFlush(msg);
                  }
                });
              }
            });
        cf = bootstrap.connect(this.host, this.port);
        cf.addListener(new ChannelFutureListener() {
          public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
              future.channel().writeAndFlush(msg);
            } else {
              ctx.channel().close();
            }
          }
        });
      } else {
        cf.channel().writeAndFlush(msg);
      }
    }
  }
}
