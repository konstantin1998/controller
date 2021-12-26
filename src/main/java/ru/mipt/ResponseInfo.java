package ru.mipt;

public class ResponseInfo {
    private final int status;
    private final long receivedTime;

    public ResponseInfo(int status, long receivedTime) {
        this.status = status;
        this.receivedTime = receivedTime;
    }

    public int getStatus() {
        return status;
    }

    public long getReceivedTime() {
        return receivedTime;
    }
}
