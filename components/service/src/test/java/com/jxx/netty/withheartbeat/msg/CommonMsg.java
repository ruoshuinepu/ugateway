package com.jxx.netty.withheartbeat.msg;

import java.io.Serializable;

public class CommonMsg extends Header {

    private long type;
    private String code;
    private Object data;


    public long getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
