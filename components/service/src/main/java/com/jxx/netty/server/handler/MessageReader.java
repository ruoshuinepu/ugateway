package com.jxx.netty.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.jxx.netty.server.msg.CommonMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class MessageReader extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(MessageReader.class);
    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }



    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println("channel 注册");
        logger.info("获取到连接，welcome:{},连接地址：{}",channelHandlerContext.channel().hashCode(),channelHandlerContext.channel().remoteAddress());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
            channelHandlerContext.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        CommonMsg msg = (CommonMsg) o;
        if(msg==null||msg.getCode()==null){
            logger.error("消息错误，消息为空");
        }
        logger.info("获取到信息：{}",(String)o);
        registerChannel(channelHandlerContext,msg);

    }

    private void registerChannel(ChannelHandlerContext channelHandlerContext, CommonMsg msg) {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(o instanceof IdleStateEvent){
            IdleState idleState  = (IdleState)o;
            if(idleState==IdleState.READER_IDLE){
                logger.error("连接超时，连接设备地址：{}",channelHandlerContext.channel().remoteAddress());
            }
        }
    }



    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

}
