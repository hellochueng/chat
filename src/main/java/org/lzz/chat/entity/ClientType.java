package org.lzz.chat.entity;

public enum ClientType {
    app(0,"appClient"),
    pc(1,"pcClient");

    private Integer code;

    private String info;

    ClientType(Integer code,String info) {
        this.code=code;
        this.info=info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
