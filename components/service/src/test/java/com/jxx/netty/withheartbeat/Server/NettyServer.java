package com.jxx.netty.withheartbeat.Server;

import com.jxx.netty.withheartbeat.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private static  final int PORT = 8001;

    public static void start(int port) throws  Exception{
        EventLoopGroup boss = new NioEventLoopGroup();//负责接收连接
        EventLoopGroup worker = new NioEventLoopGroup();//负责传输数据
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boss,worker).channel(NioServerSocketChannel.class).childHandler(new ServerChannelInitializer());
            ChannelFuture future = bootstrap.bind(12000).sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
