package ru.mipt;

import java.util.Map;

public class ReportBuilder {
    private final long expirationTimeoutMillis;
    private final long nSentRequests;

    public ReportBuilder(long expirationTimeoutMillis, long nSentRequests) {
        this.expirationTimeoutMillis = expirationTimeoutMillis;
        this.nSentRequests = nSentRequests;
    }


    public Report buildReport(Map<String, Long> sentRequests, Map<String, ResponseInfo> receivedResponses) {
        int success = 0;
        int errors = 0;
        int lost = 0;
        int expired = 0;

        for(Map.Entry<String, Long> entry: sentRequests.entrySet()) {
            ResponseInfo info = receivedResponses.get(entry.getKey());
            if (isLost(info)) {
                lost++;
                continue;
            }

            if (isExpired(entry, info)) {
                expired++;
                continue;
            }

            if (isSuccess(info)) {
                success++;
                continue;
            }

            if(isError(info)) {
                errors++;
            }
        }
        Report report = new Report();
        report.setExpired(expired);
        report.setSuccess(success);
        report.setLost(lost);
        report.setErrors(errors);
        report.setSent((int) nSentRequests);
        return report;
    }

    private boolean isError(ResponseInfo info) {
        return info.getStatus() == 500;
    }

    private boolean isSuccess(ResponseInfo info) {
        return info.getStatus() == 200;
    }

    private boolean isLost(ResponseInfo info) {
        return info == null;
    }

    private boolean isExpired(Map.Entry<String, Long> entry, ResponseInfo info) {
        return info.getReceivedTime() - entry.getValue() > expirationTimeoutMillis;
    }
}
