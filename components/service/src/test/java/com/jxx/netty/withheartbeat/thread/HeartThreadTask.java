package com.jxx.netty.withheartbeat.thread;

import com.jxx.netty.withheartbeat.msg.CommonMsg;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class HeartThreadTask extends ThreadChannelTask {

    private ChannelHandlerContext ctx;
    private String msg;

    public HeartThreadTask(ChannelHandlerContext ctx,String msg) {
        this.ctx = ctx;
        this.msg = msg;
    }

    @Override
    public void run() {
        String backMsg = "{\"code\":0}\n";
        ctx.writeAndFlush(Unpooled.copiedBuffer(backMsg, CharsetUtil.UTF_8));
    }
}
