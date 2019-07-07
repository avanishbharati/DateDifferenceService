package com.avanishbharati.datedifferenceservice.model;

import java.util.Objects;

public class DateProperties {

    private int dayValue;
    private int monthValue;
    private int yearValue;

    public DateProperties() {
    }

    public int getDayValue() {
        return dayValue;
    }

    public void setDayValue(int dayValue) {
        this.dayValue = dayValue;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }

    public int getYearValue() {
        return yearValue;
    }

    public void setYearValue(int yearValue) {
        this.yearValue = yearValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateProperties that = (DateProperties) o;
        return dayValue == that.dayValue &&
            monthValue == that.monthValue &&
            yearValue == that.yearValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayValue, monthValue, yearValue);
    }

    @Override
    public String toString() {
        return "DateProperties{" +
            "dayValue=" + dayValue +
            ", monthValue=" + monthValue +
            ", yearValue=" + yearValue +
            '}';
    }
}
