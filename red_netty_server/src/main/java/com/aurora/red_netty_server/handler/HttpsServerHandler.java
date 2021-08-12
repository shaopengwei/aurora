package com.aurora.red_netty_server.handler;

import com.aurora.red_netty_server.util.HttpRequestUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http2.HttpUtil;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetAddress;
import java.net.URI;
import java.util.Map;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/12 17:07
 */
public class HttpsServerHandler extends ChannelHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof HttpRequest) {
      HttpRequest request = (HttpRequest) msg;
      System.out.println("method" + request.method());
      System.out.println("uri" + request.uri());
      FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
      httpResponse.content().writeBytes("https".getBytes());
      httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
      httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content().readableBytes());
      httpResponse.headers().set(HttpHeaderNames.HOST, "");
      ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
