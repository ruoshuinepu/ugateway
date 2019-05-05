package com.jxx.netty.server;

import com.jxx.netty.server.handler.MessageReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class ServerChannalInitialier extends ChannelInitializer<SocketChannel> {
    @Autowired
    private MessageReader messageReader;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("\n".getBytes());
        //使用DelimiterBasedFrameDecoder解决netty中粘包问题
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(8192,buf));
        socketChannel.pipeline().addLast("idleHandler",new IdleStateHandler(120,120,120, TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast("messageReader",messageReader);

    }
}
