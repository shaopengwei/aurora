package com.aurora.red_netty_server.handler;

import com.aurora.red_netty_server.util.HttpRequestUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.Map;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-11 0:15
 */
public class HttpServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            // 获取到请求对象
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println(httpRequest.uri());
            System.out.println(httpRequest.headers().get("host"));

            // 获取请求参数 GET/POST
            Map<String, String> requestParams = HttpRequestUtil.getHttpRequestParam(ctx, httpRequest);
            System.out.println(requestParams);

            // 对 uri 请求进行判断
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }

            // 处理业务逻辑，生成返回结果

            // 回复信息给客户端 [http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);

            // 构造一个 httpresponse 的相应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将构建好 response返回
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
