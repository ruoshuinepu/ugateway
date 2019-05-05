import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ClientReader extends ChannelInboundHandlerAdapter {

    Logger logger = Logger.getLogger(this.getClass());
    public final ByteBuf buf;
    public ClientReader(){
        byte[] bytes = "hello server ".getBytes();
        buf = Unpooled.copiedBuffer(bytes);

    }
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        String reportMsg1="{\"code\":107,\"header\":{\"devicecode\":\"7864e60100fd\",\"ver\": 1},\"data\":{}}\n";
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(reportMsg1, CharsetUtil.UTF_8)); // 发送心跳给服务器
        ScheduledFuture<?> future =  channelHandlerContext.executor().scheduleAtFixedRate(new HeartBeatTask(channelHandlerContext),0,50, TimeUnit.SECONDS);
       future.addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if(future.isSuccess()){
                    logger.info("执行完成：{}");
                }
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        String msg = (String) o;
        System.out.println(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent){
                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                if(idleStateEvent.state()== IdleState.READER_IDLE){
                    System.out.println("连接超时");
                    channelHandlerContext.close();
                }
            }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

    }
    class HeartBeatTask implements  Runnable{

        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            String reportMsg="{\"code\":101,\"type\":100,\"header\":{\"timestamp\":\"2015-02-03 19:15:00\",\"devicecode\":\"000822f5a063\",\"ver\":1,\"sign\":\"chen\"},\"arcode\":[254,256,256],\"srcode\":[534, 544, 290, 290, 3174710, 256, 5, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256]}\n";
            String cmdMsg="{\"code\":100,\"type\":100,\"header\":{\"timestamp\":\"2015-02-03 19:15:00\",\"devicecode\":\"000822f5a063\",\"ver\":1,\"sign\":\"chen\"},\"cmd\":100002,\"single\":[256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 256, 1, 256, 1, 256, 256, 256, 256, 256, 1, 256, 256, 1, 256, 1],\"combin\":[16, 26, 256, 256, 256, 256, 5, 256, 256, 256, 256, 256, 256, 256, 256]}\n";
            String heartMsg="{\"code\":1}\n";
            String parameterMsg="{\"code\":103,\"data\":{\"brand\":\"haier\",\"model\":\"bcd-251wdcpu1\",\"reportTime\":\"0\",\"romVersion\":\"HAIER_10IN_V1.13\",\"softVersion\":\"1.1.0125\",\"type\":\"fridge\",\"romFactory\":\"greenmotive+jZd4WcCieKm/yuasLmRZ9aREjRJMKoAnnAkkwZ4Kw9kiQwJmzTYsd+bicYOgJFUARxzMkEvMKK7Fzfl90oDCsFqV/crtTX2PgrKjaX93Fe/+hTu96t94ydYlnN7lvG44p30Fo/A6tOy0M2mzsKJm1evpvG2adFWFxq3QnE0RBxA=\",\"lo\":\"114.322987\",\"la\":\"37.983424\",\"localAppVersionMap\":{\"recipeModule\":\"1.0.4\",\"shopModule\":\"1.0.5\",\"mediaModule\":\"1.0.8\"}, \"thirdAppVersionMap\":{\"thirdRecipeModule\":\"2.0.1\", \"thirdShopModule\":\"2.0.1\",\"thirdMediaModule\":\"2.0.3\"}},\"header\":{\"devicecode\":\"000822333e78\",\"ver\":1}}\n";
            reportMsg="{\"arcode\":[1,0,0],\"header\":{\"devicecode\":\"000822c97470\",\"ver\":15696},\"srcode\":[3,-19,256,256,256,256,256,256,256,256,256,256,256,1,0,256],\"code\":101}\n";
            //parameterMsg="{\"data\":{\"brand\":\"whirlpool\",\"model\":\"401\",\"reportTime\":\"0\",\"softVersion\":\"1.6.785\",\"type\":\"fridge\"},\"header\":{\"devicecode\":\"000822333e78\",\"ver\":16785},\"code\":103}\n";
            String reportMsg1="{\"code\":107,\"header\":{\"devicecode\":\"7864e60100fd\",\"ver\": 1},\"data\":{}}\n";
            String apkStatus="{\"code\":105,\"header\":{\"devicecode\":\"0008227d09c5\",\"ver\": 1},\"data\":{\"runTime\":\"360\",\"ramFreeSpace\":\"300\",\"romDataFreeSpace\":\"1400\",\"romExtFreeSpace\":\"1000\",\"frameRate\":\"30\",\"crashCounter\":\"3\",\"lastCrashTime\":\"2015年10月3日\"}}\n";
            String controller = "{\"code\":109,\"header\":{\"devicecode\": \"7864e60100fd\",\"ver\":1},\"data\":{\"info\":\"030000002D1900000000\"}}\n";
            ctx.writeAndFlush(Unpooled.copiedBuffer(parameterMsg, CharsetUtil.UTF_8)); // 发送心跳给服务器
            ctx.writeAndFlush(Unpooled.copiedBuffer(reportMsg1, CharsetUtil.UTF_8)); // 发送心跳给服务器
            ctx.writeAndFlush(Unpooled.copiedBuffer(heartMsg, CharsetUtil.UTF_8)); // 发送心跳给服务器
        }
    }
}
