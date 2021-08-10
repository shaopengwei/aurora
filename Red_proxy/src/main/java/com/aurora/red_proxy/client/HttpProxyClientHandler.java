package com.aurora.red_proxy.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/10 14:51
 */
public class HttpProxyClientHandler extends ChannelHandlerAdapter {

  private Channel clientChannel;

  public HttpProxyClientHandler(Channel clientChannel) {
    this.clientChannel = clientChannel;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    FullHttpResponse response = (FullHttpResponse) msg;
    //修改http响应体返回至客户端
    response.headers().add("test","from proxy");
    clientChannel.writeAndFlush(msg);
  }
}
