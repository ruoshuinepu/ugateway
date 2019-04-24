package com.jxx.netty.server.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;


import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

public abstract class AbstractHttpXmlRequestEncoder<T> extends MessageToMessageEncoder<T> {
    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;
    private final static String CHARSET_NAME = "UTF_8";

    protected ByteBuf encoder0(ChannelHandlerContext ctx, Object body) throws Exception {
        factory = BindingDirectory.getFactory(body.getClass());
        IMarshallingContext mctx = factory.createMarshallingContext();
        writer = new StringWriter();

        mctx.setIndent(2);
        mctx.marshalDocument(body,CHARSET_NAME,null,writer);
        String xmlBody =  writer.toString();
        ByteBuf buffer = Unpooled.copiedBuffer(xmlBody, Charset.forName("UTF-8"));
        return buffer;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(writer!=null){
            writer.close();
            writer = null;
        }
    }
}