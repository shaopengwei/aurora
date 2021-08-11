package com.aurora.red_netty_server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-11 23:04
 */
public class HttpRequestClientHandler extends ChannelHandlerAdapter {

    private Channel clientChannel;
    private HttpRequest clientRequest;

    public HttpRequestClientHandler(Channel clientChannel, HttpRequest clientRequest){
        this.clientChannel = clientChannel;
        this.clientRequest = clientRequest;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI(clientRequest.uri());
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().addInt(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        request.headers().add(HttpHeaderNames.HOST, clientRequest.headers().get("host"));
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg-> " + msg);
        FullHttpResponse response = (FullHttpResponse) msg;
        response.headers().add("test","from proxy");
        System.out.println("response: "+response);
        clientChannel.writeAndFlush(response);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
