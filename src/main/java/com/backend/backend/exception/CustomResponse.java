package com.backend.backend.exception;

public class CustomResponse<T> {
    private String message;
    private T data;

    // No-args constructor
    public CustomResponse() {
    }

    // All-args constructor
    public CustomResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for data
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CustomResponse{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
