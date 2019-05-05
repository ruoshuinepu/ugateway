package com.jxx.netty.server.msg;

import java.io.Serializable;

public class Header implements Serializable {

    private String mobileKey;// 当多个app同时链接一个设备时，根据该字段区分不同app，默认为连接的hashcode
    private String devicecode; // 设备码
    private Long ver; // 客户端版本
    private String sign; // 签名
    private String timestamp; // 时间戳


    public String getMobileKey() {
        return mobileKey;
    }

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
