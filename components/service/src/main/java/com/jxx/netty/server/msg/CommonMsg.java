package com.jxx.netty.server.msg;

public class CommonMsg extends Header {
    private long type;
    private String code;
    private Object data;


    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
