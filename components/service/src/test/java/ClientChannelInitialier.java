import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ClientChannelInitialier extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("\n".getBytes());
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(8192,buf));
        socketChannel.pipeline().addLast("IdleStateHandler",new IdleStateHandler(120,120,120,TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast("clientreader",new ClientReader());
    }
}
