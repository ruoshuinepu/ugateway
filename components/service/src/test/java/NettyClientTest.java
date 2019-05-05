import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class NettyClientTest {

    Logger logger = Logger.getLogger(this.getClass());
    @Test
    public void run(){
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(worker).channel(NioSocketChannel.class).handler(new ClientChannelInitialier());
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(InetAddress.getByAddress(new byte[] {127, 0, 0, 1}),8001)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException | UnknownHostException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }
}
