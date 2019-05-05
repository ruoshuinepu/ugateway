package com.jxx.server.start;

import com.jxx.netty.server.PortServer;
import com.unilife.commons.util.config.RemoteProperties;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerStart {

    private static int port;
    public static ClassPathXmlApplicationContext ctx = null;

    public static void main(String[] args) {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8001;
        }
        run();
    }

    private static void run() {
        if (ctx == null) {
            init();
        }
        PortServer portServer = getBean(PortServer.class);
        portServer.setPort(port);
        portServer.run();
    }
    public static <T> T getBean(Class<T> requiredType){
        if(ctx==null){
            init();
        }
        return ctx.getBean(requiredType);
    }
    private static void init() {
        RemoteProperties.initLog();
        ctx = new ClassPathXmlApplicationContext("classpath*:/*.xml");
        Logger logger = Logger.getLogger(ServerStart.class);
        logger.info("开始加载xml");
    }

}
