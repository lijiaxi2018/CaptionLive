package com.aguri.captionlive.common.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Resp {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;






    private Resp() {
    }

    public Resp(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class Builder {
        private String message;
        private Object data;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Resp build() {
            Resp resp = new Resp();
            resp.setMessage(message);
            resp.setData(data);
            return resp;
        }
    }

    public static Resp ok(Object data) {
        return new Builder()
                .message("success")
                .data(data)
                .build();
    }

    public static Resp ok() {
        return new Builder()
                .message("success")
                .build();
    }

    public static Resp failed(String message) {
        return new Builder()
                .message(message)
                .build();
    }
}
