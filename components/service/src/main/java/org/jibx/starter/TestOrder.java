package org.jibx.starter;

import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class TestOrder {

    private IBindingFactory factory = null;
    private StringWriter writer =null ;
    private StringReader reader = null;
    private final static String CHARSET_NAME="UTF_8";

    private String encode2Xml(Order order) throws JiBXException, IOException {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order,CHARSET_NAME,null,writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr.toString());
        return xmlStr;
    }
    private <T>T decode2Object(String xmlBody) throws JiBXException {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        T t =  (T) uctx.unmarshalDocument(reader);
        return t;
    }

    public static void main(String[] args) throws JiBXException, IOException {
        TestOrder test = new TestOrder();
        Order order = OrderFactory.create(123);
        String xmlBody = test.encode2Xml(order);
        Order order1 = test.decode2Object(xmlBody);
        System.out.println(order1.getOrderNumber());

    }

}
