package ru.mipt;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ReportBuilderTest {
    @Test
    public void shouldGenerateCorrectReport() {
        Map<String, Long> sentRequests = new HashMap<>();
        sentRequests.put("1", 0L);
        sentRequests.put("2", 10L);
        sentRequests.put("3", 20L);
        sentRequests.put("4", 30L);
        sentRequests.put("5", 40L);
        sentRequests.put("6", 50L);
        sentRequests.put("7", 60L);
        sentRequests.put("8", 70L);

        Map<String, ResponseInfo> receivedResponses = new HashMap<>();
        receivedResponses.put("1", new ResponseInfo(200, 50L));
        receivedResponses.put("2", new ResponseInfo(200, 60L));
        receivedResponses.put("3", new ResponseInfo(200, 70L));
        receivedResponses.put("4", new ResponseInfo(500, 80L));
        receivedResponses.put("5", new ResponseInfo(500, 90L));
        receivedResponses.put("6", new ResponseInfo(200, 120L));
        receivedResponses.put("7", new ResponseInfo(500, 130L));

        long expirationTimeout = 60;

        ReportBuilder rb = new ReportBuilder(expirationTimeout, sentRequests.size());
        Report report = rb.buildReport(sentRequests, receivedResponses);

        Report expectedReport = new Report();
        expectedReport.setErrors(2);
        expectedReport.setLost(1);
        expectedReport.setSuccess(3);
        expectedReport.setExpired(2);

        assertEquals(expectedReport.getErrors(), report.getErrors());
        assertEquals(expectedReport.getExpired(), report.getExpired());
        assertEquals(expectedReport.getLost(), report.getLost());
        assertEquals(expectedReport.getSuccess(), report.getSuccess());
    }
}