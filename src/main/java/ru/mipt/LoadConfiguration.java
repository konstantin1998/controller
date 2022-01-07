package ru.mipt;

public class LoadConfiguration {
    private int rate;
    private int expirationTimeout;
    private int duration;
    private int trafficThreshold = Integer.MAX_VALUE;

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

    public int getTrafficThreshold() {
        return trafficThreshold;
    }

    public void setTrafficThreshold(int trafficThreshold) {
        this.trafficThreshold = trafficThreshold;
    }
}
