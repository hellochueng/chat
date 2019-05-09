package org.lzz.chat.entity;

import java.io.Serializable;

public class Result implements Serializable{
    private Integer code;

    private String message;

    private Object object;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }


    public Result() {
        super();
    }

    public Result(Integer code, String message, Object object){
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public Result(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
