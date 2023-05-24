package com.aguri.captionlive.common.exception.resp;

public class Resp {
    public String message;
    public Object data;

    public Resp(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public static Resp success(Object data) {
        return new Resp("success", data);
    }

    public static Resp failed(String message) {
        return new Resp(message, null);
    }

}
