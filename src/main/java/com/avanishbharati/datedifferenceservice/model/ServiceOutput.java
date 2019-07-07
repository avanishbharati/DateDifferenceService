package com.avanishbharati.datedifferenceservice.model;

public class ServiceOutput {

    private int difference;
    private String earliestDate;
    private String latestDate;

    public ServiceOutput() {
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
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
        return "ServiceOutput{" +
            "difference=" + difference +
            ", earliestDate='" + earliestDate + '\'' +
            ", latestDate='" + latestDate + '\'' +
            '}';
    }
}
