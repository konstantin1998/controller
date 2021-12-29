package ru.mipt;

public class Report {
    private int sent;
    private int errors;
    private int success;
    private int lost;
    private int expired;

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public int getErrors() {
        return errors;
    }

    public int getLost() {
        return lost;
    }

    public int getSent() {
        return sent;
    }

    public int getSuccess() {
        return success;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
