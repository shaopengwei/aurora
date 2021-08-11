package com.aurora.red_netty_server.handler;

import com.aurora.red_netty_server.util.HttpRequestUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-11 21:33
 */
public class HttpServerForwardHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            String hostname = HttpRequestUtil.getHttpRequestHostname(ctx, httpRequest);
            int port = HttpRequestUtil.getHttpRequestPort(ctx, httpRequest);

            if (!"1.117.231.230".equals(hostname)) {
                System.out.println("请求了其他服务, 不做响应");
                return;
            }

            // 构造 http request client 将请求转发出去，并获取返回值
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            try {
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new HttpClientCodec());
                                ch.pipeline().addLast(new HttpObjectAggregator(65536));
                                ch.pipeline().addLast(new HttpContentDecompressor());
                                ch.pipeline().addLast(new HttpRequestClientHandler(ctx.channel(), httpRequest));
                            }
                        });
                ChannelFuture f = b.connect(hostname, port).sync();
                f.channel().closeFuture().sync();
            } finally {
                group.shutdownGracefully().sync();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
