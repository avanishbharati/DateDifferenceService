package com.avanishbharati.datedifferenceservice.model;

public class ServiceRequest {

    private String earliestDate;
    private String latestDate;

    public ServiceRequest() {
    }

    public String getEarliestDate() {
        return earliestDate;
    }

    public void setEarliestDate(String earliestDate) {
        this.earliestDate = earliestDate;
    }

    public String getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(String latestDate) {
        this.latestDate = latestDate;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
            ", earliestDate='" + earliestDate + '\'' +
            ", latestDate='" + latestDate + '\'' +
            '}';
    }
}
