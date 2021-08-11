package com.aurora.red_netty_server.handler;

import com.aurora.red_netty_server.entity.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author shaopengwei@hotmail.com
 * @Description 实现时间服务器
 * @create 2021-08-11 11:53
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    /**
     * Because we are going to ignore any received data but to send a message as soon as a connection is established,
     * we cannot use the channelRead() method this time. Instead, we should override the channelActive() method.
     * <p>
     * the channelActive() method will be invoked when a connection is established and ready to generate traffic.
     *
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        final ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
