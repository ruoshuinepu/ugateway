import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

public class NettyClientTest {

    @Test
    public void run(){
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(worker).channel(NioSocketChannel.class).handler(new ClientChannelInitialier());
            ChannelFuture future = bootstrap.connect("127.0.0.1",8001).sync();
            future.channel().close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }
}
