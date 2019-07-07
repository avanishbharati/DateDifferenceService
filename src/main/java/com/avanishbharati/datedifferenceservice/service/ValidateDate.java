package com.avanishbharati.datedifferenceservice.service;


import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import com.avanishbharati.datedifferenceservice.model.DateProperties;
import com.avanishbharati.datedifferenceservice.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/***
 * *
 * Validate date values from string
 */
public class ValidateDate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateDate.class);

    private String mDateString;
    private DateProperties mDateProperties = new DateProperties();

    /***
     * Returns valid date properties from a date string of the format "DD MMM YYYY"
     * @param dateString
     * @return DateProperties
     * @throws ConvertDataException
     */
    public static DateProperties parse(String dateString) throws ConvertDataException {

        ValidateDate validDateConverter = new ValidateDate(dateString);
        DateProperties validDateProperties = validDateConverter.getValidDateProperties();
        return validDateProperties;
    }


    private ValidateDate(String dateString) {
        mDateString = dateString;
        validateInput();
    }

    private void validateInput(){

        String[] data = mDateString.split(" ");

        LOGGER.info("Input data as string: {}", Arrays.toString(data));

        if(data.length != 3){
            LOGGER.error("Invalid length or format {}", mDateString);
            throw new ConvertDataException("Invalid format found");
        }

        try{

            int day = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1]);
            int year = Integer.parseInt(data[2]);

            mDateProperties.setDayValue(day);
            mDateProperties.setMonthValue(month);
            mDateProperties.setYearValue(year);

        } catch (NumberFormatException ex){
            LOGGER.error("Invalid value of DD, MM or YYYY", ex);
            throw new ConvertDataException("Invalid value of DD, MM or YYYY");
        }

        if(!validRangeOfYear()){
            LOGGER.error("The value {}, as year is out of supported range, (supported range is between 1900 to 2010)", mDateProperties.getYearValue());
            throw new ConvertDataException("Unsupported value of year");
        }

        if(!validMonth()){
            LOGGER.error("Invalid value of Month: {}", mDateProperties.getMonthValue());
            throw new ConvertDataException("Invalid value of month");
        }

        if(!validDate()){
            LOGGER.error("Invalid value of day : {}", mDateProperties.getDayValue());
            throw new ConvertDataException("Invalid value of day");
        }

    }

    private DateProperties getValidDateProperties() {
        return mDateProperties;
    }

    private Boolean validMonth(){

        if ((1 <= mDateProperties.getMonthValue()) && (mDateProperties.getMonthValue() <= 12)){
            return true;
        }

        return false;
    }

    private Boolean validRangeOfYear(){

        if ((1900 <= mDateProperties.getYearValue()) && (mDateProperties.getYearValue() <= 2010)) {
            return true;
        }

        return false;
    }

    private Boolean validDate(){

        if((DateUtils.getMonthDays().get(mDateProperties.getMonthValue()) == 30) &&
            (mDateProperties.getDayValue() <= 30)){
            return true;
        }

        if((DateUtils.getMonthDays().get(mDateProperties.getMonthValue()) == 31) &&
            (mDateProperties.getDayValue() <= 31)){
            return true;
        }

        if(!DateUtils.isLeapYear( mDateProperties.getYearValue())) {
            if((DateUtils.getMonthDays().get(mDateProperties.getMonthValue()) == 28) &&
                (mDateProperties.getDayValue() <= 28)){
                return true;
            }
        } else {
            if((DateUtils.getLeapFebDays().get(mDateProperties.getMonthValue()) == 29) &&
                (mDateProperties.getDayValue() <= 29)){
                return true;
            }
        }

        return false;
    }


}
