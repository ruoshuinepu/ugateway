package com.jxx.netty.server.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import java.io.StringReader;
import java.nio.charset.Charset;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
    private IBindingFactory factory = null;
    private StringReader reader = null;
    private static final  String CHARSET_NAME="UTF-8";


    protected  Object decoder0(ChannelHandlerContext ctx, ByteBuf buf,Class<?> clazz) throws JiBXException {
        factory = BindingDirectory.getFactory(clazz);
        IUnmarshallingContext ictx = factory.createUnmarshallingContext();
        String body = buf.toString(Charset.forName("UTF-8"));
        reader = new StringReader(body);
        Object object = ictx.unmarshalDocument(reader);
        return object;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(reader!=null){
            reader.close();
            reader = null;
        }
    }
}
