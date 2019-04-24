package com.jxx.cache.redis;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private static Map<String, ChannelHandlerContext> deviceConnMap = new ConcurrentHashMap<>();

    public static void saveDeviceChannel(String deviceCode, ChannelHandlerContext ctx  ) {
        // 存储连接
        deviceConnMap.put( deviceCode, ctx );
    }
    public static ChannelHandlerContext getDeviceChannel(String deviceCode){
        return deviceConnMap.get(deviceCode);
    }

}
