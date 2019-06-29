package com.jxx.netty.withheartbeat.Client.handler;

import com.unilife.commons.util.GsonUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.*;

public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String backMsg = "{\"code\":103,\"data\":{\"brand\":\"haier\",\"la\":\"\",\"lo\":\"\",\"localAppVersionMap\":{\"cn.xiaoneng.wulian\":\"\",\"com.haiersmart.shop\":\"\",\"com.haiersmart.user\":\"\",\"com.haiersmart.web\":\"\",\"com.tencent.deviceqq\":\"\",\"com.unilife.fridge.haierbase.food\":\"1.0.18188\",\"com.unilife.fridge.haierbase.mb\":\"1.0.18173\",\"com.unilife.fridge.haierbase.media\":\"\",\"com.unilife.fridge.haierbase.recipe\":\"\",\"com.unilife.fridge.haierbase.shop\":\"\",\"com.unilife.fridge.haierbase.tft\":\"1.0.18190\",\"com.unilife.ijkplayer\":\"\",\"com.unilife.um_alipay\":\"\"},\"model\":\"HRB-758SIKGU1111\",\"platform\":\"rk3288\",\"reportTime\":\"0\",\"romVersion\":\"HAIER_HTF552_UOS_RK3288_0040\",\"softVersion\":\"1.0.18190\",\"thirdAppVersionMap\":{},\"type\":\"fridge\"},\"header\":{\"devicecode\":\"6c21a214c6b2\",\"ver\":1018190}}\n";
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
