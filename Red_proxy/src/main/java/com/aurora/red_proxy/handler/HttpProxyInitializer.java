package com.aurora.red_proxy.handler;

import com.aurora.red_proxy.client.HttpProxyClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/10 14:49
 */
public class HttpProxyInitializer extends ChannelInitializer {

  private Channel clientChannel;

  public HttpProxyInitializer(Channel clientChannel) {
    this.clientChannel = clientChannel;
  }

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ch.pipeline().addLast(new HttpClientCodec());
    ch.pipeline().addLast(new HttpObjectAggregator(6553600));
    ch.pipeline().addLast(new HttpProxyClientHandler(clientChannel));
  }
}
