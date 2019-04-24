package com.jxx.netty.server.http.handler;

import com.google.common.net.InetAddresses;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.util.List;

public class HttpXmlRequestEncoder extends AbstractHttpXmlRequestEncoder<HttpXmlRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
        ByteBuf buf = encoder0(ctx, msg.getBody());
        FullHttpRequest request = msg.getRequest();
        if (request == null) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,"/do",buf);
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());
            headers.set(HttpHeaderNames.CONNECTION,"close");
            headers.set(HttpHeaderNames.ACCEPT_ENCODING,"gzip"+','+"deflate");
            headers.set(HttpHeaderNames.ACCEPT_CHARSET,"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE,"zh");
            headers.set(HttpHeaderNames.USER_AGENT,"Netty");
            headers.set(HttpHeaderNames.ACCEPT,"text/html,application/xhtml+xml,application/xml");
        }
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
        out.add(request);
    }
}
