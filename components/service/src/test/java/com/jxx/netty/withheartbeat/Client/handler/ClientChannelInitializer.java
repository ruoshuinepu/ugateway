package com.jxx.netty.withheartbeat.Client.handler;

import com.jxx.netty.withheartbeat.handler.MessageReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline =  ch.pipeline();
        ByteBuf buf = Unpooled.copiedBuffer("\n".getBytes());
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192,buf));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast("IdleStateHandler",new IdleStateHandler(200,200,200, TimeUnit.SECONDS));
        pipeline.addLast("HeartBeatReqHandler",new HeartBeatReqHandler() );
    }
}
