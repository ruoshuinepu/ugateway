package com.jxx.netty.withheartbeat;

import com.jxx.netty.withheartbeat.Server.NettyServer;

public class ServerStart {

    public static void main(String[] args)  {
        try {
            NettyServer.start(8001);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
