package com.aurora.red_netty_client.client;

import com.aurora.red_netty_client.handler.HttpRequestClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-10 23:23
 */
public class Client {

    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        // Bootstrap is similar to ServerBootstrap except that
        // it's for non-server channels such as a client-side or connectionless channel.
        Bootstrap b = new Bootstrap();
        try {
            //If you specify only one EventLoopGroup, it will be used both as a boss group and as a worker group.
            // The boss worker is not used for the client side though.
            b.group(group);
            // Instead of NioServerSocketChannel, NioSocketChannel is being used to create a client-side Channel.
            b.channel(NioSocketChannel.class);
            // Note that we do not use childOption() here unlike we did with ServerBootstrap
            // because the client-side SocketChannel does not have a parent.
            b.option(ChannelOption.SO_KEEPALIVE, true);
            // add handler
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // http request
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(65536));
                    ch.pipeline().addLast(new HttpContentDecompressor());
                    ch.pipeline().addLast(new HttpRequestClientHandler());
                    // timer handler
                    // ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                    // echo handler
                    // ch.pipeline().addLast(new EchoClientHandler());
                }
            });
            // We should call the connect() method instead of the bind() method.
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}
