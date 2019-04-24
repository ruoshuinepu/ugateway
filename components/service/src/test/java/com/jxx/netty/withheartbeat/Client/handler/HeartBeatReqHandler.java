package com.jxx.netty.withheartbeat.Client.handler;

import com.unilife.commons.util.GsonUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String backMsg = "{\"devicecode\":\"111111\",\"code\":0}\n";
        ctx.writeAndFlush(Unpooled.copiedBuffer(backMsg, CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str = (String) msg;
        System.out.println(""+str);
        ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,20000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常");
        super.exceptionCaught(ctx, cause);
    }
    private class  HeartBeatTask implements  Runnable{

        private final  ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            String backMsg = "{\"devicecode\":\"111111\",\"code\":0}\n";
            ctx.writeAndFlush(Unpooled.copiedBuffer(backMsg, CharsetUtil.UTF_8));
        }
    }
}
