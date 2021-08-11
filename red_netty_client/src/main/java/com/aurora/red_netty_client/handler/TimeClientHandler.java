package com.aurora.red_netty_client.handler;

import com.aurora.red_netty_client.entity.UnixTime;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author shaopengwei@hotmail.com
 * @Description 将时间服务器返回的值转换成可读样式，然后关闭连接
 * @create 2021-08-11 12:15
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    /**
     * server 或 client 都会出现一个问题：由于接收到的数据不完整导致无法读取，即数据碎片化问题
     * 解决办法：
     *  1.创建一个内部的累积 buffer，直到达到读取数据的大小才开始执行业务逻辑。比如使用 handlerAdded 和 handlerRemoved 方法，
     *  netty 会在每次有数据进来时调用 channelRead 方法，方法内部会判断 buffer 大小是否满足要求。
     *  2.方法1适合于接收字段比较简单的情况，如果接收字段比较复杂就不好维护，可以考虑单独创建一个 handler 用于处理数据碎片化问题，
     *  然后在 channelPipeline 中添加多个 handler
     *
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
