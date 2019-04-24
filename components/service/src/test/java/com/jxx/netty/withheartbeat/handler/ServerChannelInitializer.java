package com.jxx.netty.withheartbeat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline =  ch.pipeline();
        ByteBuf delimiter = Unpooled.copiedBuffer("\n".getBytes()); // 分隔符

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, delimiter));
        pipeline.addLast(new StringDecoder());

        pipeline.addLast("idleStateHandler", new IdleStateHandler(120, 120, 120));
        pipeline.addLast("messageReader", new MessageReader());
    }
}
