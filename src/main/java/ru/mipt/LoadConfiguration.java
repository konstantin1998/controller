package ru.mipt;

public class LoadConfiguration {
    private int rate;
    private int expirationTimeout;
    private int duration;

    public int getDuration() {
        return duration;
    }

    public int getExpirationTimeout() {
        return expirationTimeout;
    }

    public int getRate() {
        return rate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setExpirationTimeout(int expirationTimeout) {
        this.expirationTimeout = expirationTimeout;
    }
}
