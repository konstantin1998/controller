package ru.mipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Generator extends Thread {
    private final int period;
    private final int durationSeconds;
    private final int maxBytesPerRequest;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String inputQueueUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, Long> sentRequests = new HashMap<>();

    public Generator(int rate, int durationSeconds, int trafficThreshold, String inputQueueUrl){
        super();
        period = Math.max(1, 1000 / rate);
        this.durationSeconds = durationSeconds;
        this.inputQueueUrl = inputQueueUrl;
        this.maxBytesPerRequest = trafficThreshold / rate;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        Random random = new Random();
        while(currentTime - startTime < durationSeconds * 1000L) {
            String id = String.valueOf(random.nextInt());
            Request request = new Request(id);
            sendWithRetries(request);
            currentTime = System.currentTimeMillis();
            saveRequest(id, currentTime);
            try {
                int timeToWait = period * Math.max(1, objectMapper.writeValueAsBytes(request).length / maxBytesPerRequest);
                waitMillis(timeToWait);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendWithRetries(Request request) {
        int nRetries = 3;
        int i = 0;
        while (i < nRetries) {
            try {
                restTemplate.put(inputQueueUrl, request);
                break;
            } catch (HttpClientErrorException | ResourceAccessException e) {
                i++;
                waitMillis(10);
            }
        }
    }

    private void waitMillis(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void saveRequest(String id, Long sentTime) {
        sentRequests.put(id, sentTime);
    }

    public Map<String, Long> getSentRequests() {
        return sentRequests;
    }
}
