package ru.mipt;

public class AppConfig {
    private String requestToInputQueue;
    private String requestToOutputQueue;

    public String getRequestToInputQueue() {
        return requestToInputQueue;
    }

    public String getRequestToOutputQueue() {
        return requestToOutputQueue;
    }

    public void setRequestToInputQueue(String requestToInputQueue) {
        this.requestToInputQueue = requestToInputQueue;
    }

    public void setRequestToOutputQueue(String requestToOutputQueue) {
        this.requestToOutputQueue = requestToOutputQueue;
    }
}
