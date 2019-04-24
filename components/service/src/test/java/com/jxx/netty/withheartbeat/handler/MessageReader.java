package com.jxx.netty.withheartbeat.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jxx.cache.redis.Cache;
import com.jxx.netty.withheartbeat.msg.CommonMsg;
import com.jxx.netty.withheartbeat.thread.HeartThreadTask;
import com.unilife.commons.util.GsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageReader extends ChannelInboundHandlerAdapter {

    private static ExecutorService channelThreadPool = Executors.newFixedThreadPool(10);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("获取到连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str = (String) msg;
        System.out.println(str);
        //CommonMsg commonMsg  = (CommonMsg) msg;
       // System.out.println("接收到消息：{}"+GsonUtil.toJson(msg.getClass()));
        registerChannel(ctx,str);
        disposeMessage(ctx,str);

    }

    private void disposeMessage(ChannelHandlerContext ctx, String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        switch (jsonObject.getString("code")){
            case "0":{
                    channelThreadPool.execute(new HeartThreadTask(ctx, msg));
            }
            case "103":{

            }
            case "105":{

            }
        }

    }

    private void registerChannel(ChannelHandlerContext ctx, String commonMsg) {
        JSONObject jsonObject = JSONObject.parseObject(commonMsg);
        Cache.saveDeviceChannel(jsonObject.getString("devicecode"),ctx);//保存连接

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /***
     * 用户事件触发的时候
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if(idleStateEvent.state()== IdleState.READER_IDLE){
                closeChannel(ctx);
            }
        }

    }

    private void closeChannel(ChannelHandlerContext ctx) {

    }

    /***
     * 当前channel不活跃的时候，也就是当前channel到了它生命周期末
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
