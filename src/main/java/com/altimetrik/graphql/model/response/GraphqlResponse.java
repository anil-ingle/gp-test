package com.altimetrik.graphql.model.response;

public class GraphqlResponse<T> {
    private T res;
    private String message;
    private String error;

    public GraphqlResponse(T res, String message, String error) {
        this.res = res;
        this.message = message;
        this.error = error;
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
