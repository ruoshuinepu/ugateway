package com.jxx.netty.server.http.initialier;

import com.jxx.netty.server.http.handler.HttpFileServerHander;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;


public class HttpInitialier extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf buffer = Unpooled.copiedBuffer("\n".getBytes());
/*
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(8024,buffer));
        socketChannel.pipeline().addLast(new StringDecoder());*/
        socketChannel.pipeline().addLast("httpReqDecoder",new HttpRequestDecoder());
        socketChannel.pipeline().addLast("HttpObjectAggregator",new HttpObjectAggregator(65536));
        socketChannel.pipeline().addLast("HttpRequestEncode",new HttpRequestEncoder());
        socketChannel.pipeline().addLast("ChunkedWriteHandler",new ChunkedWriteHandler());//防止传送大文件，造成内存溢出
        socketChannel.pipeline().addLast("fileServerHandler",new HttpFileServerHander());
    }
}
