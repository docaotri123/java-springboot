package com.example.demo.share.utils;

import lombok.Data;

@Data
public class ResponseObject {
    private String message;
    private Object data;

    private Object meta;

    public ResponseObject(String message, Object data, Object meta) {
        this.message = message;
        this.data = data;
        this.meta = meta;
    }

    public ResponseObject(String message, Object data) {
        this.message = message;
        this.data = data;
    }

}