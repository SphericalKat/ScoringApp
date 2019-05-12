package com.minosai.scoringapp.model;

public class ResponseModelPayload<T> extends ResponseModel {

    T payload;

    public ResponseModelPayload() {

    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
