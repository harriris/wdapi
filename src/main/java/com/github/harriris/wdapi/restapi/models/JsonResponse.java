package com.github.harriris.wdapi.restapi.models;

public class JsonResponse {
    private String message;

    public JsonResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
