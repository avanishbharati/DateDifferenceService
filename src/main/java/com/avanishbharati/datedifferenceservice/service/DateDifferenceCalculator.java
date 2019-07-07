package com.avanishbharati.datedifferenceservice.service;

import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import com.avanishbharati.datedifferenceservice.model.DateProperties;
import com.avanishbharati.datedifferenceservice.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateDifferenceCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateDifferenceCalculator.class);


    private static final String INVALID_FORMAT = "First date should be the earliest and the second date should be the latest.";

    private DateProperties fromDateProperties;
    private DateProperties endDateProperties;

    private String fromDate;
    private String endDate;


    /***
     * Returns the difference of date specified in string of format "DD MMM YYYY, DD MMM YYYY"
     * @param earliestDate
     * @param latestDate
     * @return difference
     * @throws ConvertDataException
     */
    public static int dateDifference(String earliestDate, String latestDate ) throws ConvertDataException {

        DateDifferenceCalculator dateDifferenceCalculator = new DateDifferenceCalculator(earliestDate, latestDate);

        dateDifferenceCalculator.getFromDateProperties();
        dateDifferenceCalculator.getEndDateDateProperties();
        dateDifferenceCalculator.validateEarliestLatestFromat();

        int dateDiff = dateDifferenceCalculator.getDateDifference();
        LOGGER.info("Date difference {}", dateDiff);

        return dateDiff;

    }

    private DateDifferenceCalculator( String fromDate, String endDate) {
        this.fromDate = fromDate;
        this.endDate = endDate;
    }

    private void getFromDateProperties() throws ConvertDataException{
        DateProperties dateProperties = ValidateDate.parse(fromDate);
        fromDateProperties = dateProperties;
    }

    private void getEndDateDateProperties() throws ConvertDataException{
        DateProperties dateProperties = ValidateDate.parse(endDate);
        endDateProperties = dateProperties;
    }

    private int getDifferenceOfYear(){
        return endDateProperties.getYearValue() - fromDateProperties.getYearValue();
    }

    private void validateEarliestLatestFromat(){

        int differenceOfYear = getDifferenceOfYear();

        if ( differenceOfYear < 0 ){
            throw new ConvertDataException(INVALID_FORMAT);

        } else if (differenceOfYear == 0){

            if ( endDateProperties.getMonthValue() < fromDateProperties.getMonthValue() ){
                throw new ConvertDataException(INVALID_FORMAT);

            } else if (fromDateProperties.getMonthValue() == endDateProperties.getMonthValue() ){
                if (endDateProperties.getDayValue() < fromDateProperties.getDayValue()){
                    throw new ConvertDataException(INVALID_FORMAT);
                }
            }
        }
    }

    private int getNumOfDaysYearsInDifference(){

        int yearDifference = getDifferenceOfYear();

        if(yearDifference > 1){

            int reffrenceYear = fromDateProperties.getYearValue();
            int sumOfYearInDays = 0;

            for (int i = 1; i < yearDifference; i++){
                reffrenceYear = reffrenceYear + 1;

                if(DateUtils.isLeapYear(reffrenceYear)){
                    sumOfYearInDays = sumOfYearInDays + 366;
                } else {
                    sumOfYearInDays = sumOfYearInDays + 365;
                }
            }

            LOGGER.info("SumOfYearInDays : {}", sumOfYearInDays);
            return sumOfYearInDays;
        }

        return 0;
    }

    private int getYearDaysUntilEndDate(){

        int currentMonth = endDateProperties.getMonthValue();
        int monthDay = endDateProperties.getDayValue();
        Boolean isLeapYear = DateUtils.isLeapYear(endDateProperties.getYearValue());

        return getYearDaysUntil(currentMonth, monthDay, isLeapYear );

    }

    private int getYearDaysUntilFromDate(){

        int currentMonth = fromDateProperties.getMonthValue();
        int monthDay = fromDateProperties.getDayValue();
        Boolean leapYear = DateUtils.isLeapYear(fromDateProperties.getYearValue());

        int numDays = 0;

        if (leapYear){
            numDays =  (366 - getYearDaysUntil(currentMonth,monthDay,leapYear));
        } else {
            numDays =  (365 - getYearDaysUntil(currentMonth,monthDay,leapYear));
        }

        LOGGER.info("remaining total : {}", numDays);

        return numDays;

    }

    private int getYearDaysUntil(int currentMonth, int monthDay, Boolean leapYear){
        int numDays = 0;

        if (leapYear){
            for (int i = 1; i < currentMonth; i++){
                if(i == 2){
                    numDays = DateUtils.getLeapFebDays().get(i) + numDays;
                } else {
                    numDays = DateUtils.getMonthDays().get(i) + numDays;
                }
            }

            LOGGER.info("Leap year total : {}", numDays);

        } else {
            for (int i = 1; i < currentMonth; i++){
                numDays = DateUtils.getMonthDays().get(i) + numDays;
            }
        }
        int sum = numDays + monthDay;
        LOGGER.info("getYearDaysUntil total : {}", sum);

        return sum;
    }

    private int getDateDifference(){

        if(fromDateProperties.equals(endDateProperties)){
            return 0;
        }

        int diffDays = 0;

        int getYearDaysUntilEndDate = getYearDaysUntilEndDate();
        int getYearDaysUntilFromDate = getYearDaysUntilFromDate();
        int getNumOfDaysYearsInDifference = getNumOfDaysYearsInDifference();

        diffDays = getYearDaysUntilEndDate + getYearDaysUntilFromDate + getNumOfDaysYearsInDifference;

        if(fromDateProperties.getYearValue() == endDateProperties.getYearValue()){
            if(!DateUtils.isLeapYear(fromDateProperties.getYearValue())){
                diffDays = diffDays - 365;
            } else {
                diffDays = diffDays - 366;
            }
        }

        return diffDays;
    }


}
