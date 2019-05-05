package com.jxx.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PortServer {

    private Logger logger = LoggerFactory.getLogger(PortServer.class);
    private int port;
    @Autowired
    private ServerChannalInitialier initialier;


    public void run(){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();//两个eventloopgroup,一个负责连接，一个负责读写
        ServerBootstrap bosp = new ServerBootstrap();
        try {
            bosp.group(boss,worker).channel(NioServerSocketChannel.class).childHandler(initialier);
            logger.info((new StringBuilder()).append("socket server started at port ").append(port).append('.').toString());

            //同步等待绑定完成
            ChannelFuture future = bosp.bind(port).sync();
            //同步等待关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    public void setPort(int port) {
        this.port = port;
    }
}
