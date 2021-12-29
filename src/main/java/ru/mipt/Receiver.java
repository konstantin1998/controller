package ru.mipt;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Receiver extends Thread{
    private final Map<String, ResponseInfo> receivedResponses = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final String outQueueUrl;

    public Receiver(String outQueueUrl) {
        super();
        this.outQueueUrl = outQueueUrl;
    }

    @Override
    public void run() {
        while(true) {
            Response response = receiveWithRetries();
            if(response == null) {
                System.out.println("response is not received");
                break;
            }
            saveResponse(response, System.currentTimeMillis());
            System.out.println("received responses size: " + receivedResponses.size());
        }
    }

    private Response receiveWithRetries() {
        int nRetries = 10;
        int i = 0;
        Response response = null;
        while (i < nRetries) {
            try {
                response = restTemplate.getForObject(outQueueUrl, Response.class);
                if (response != null) {
                    break;
                } else {
                    i++;
                }
            } catch (HttpClientErrorException | ResourceAccessException e) {
                i++;
                waitForValidResponse();
            }
        }
        return response;
    }

    private void waitForValidResponse() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void saveResponse(Response response, long receivedTime) {
        receivedResponses.put(response.getId(), new ResponseInfo(response.getStatusCode(), receivedTime));
    }

    public Map<String, ResponseInfo> getReceivedResponses() {
        return receivedResponses;
    }
}
