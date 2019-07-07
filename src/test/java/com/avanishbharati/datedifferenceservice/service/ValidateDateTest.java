package com.avanishbharati.datedifferenceservice.service;

import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import com.avanishbharati.datedifferenceservice.model.DateProperties;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateDateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateDateTest.class);


    @Test
    public void validInputTest(){

        DateProperties dateProperties =  ValidateDate.parse("25 02 2005");

        Assert.assertEquals(25, dateProperties.getDayValue());
        Assert.assertEquals(2, dateProperties.getMonthValue());
        Assert.assertEquals(2005, dateProperties.getYearValue());

    }

    @Test
    public void invalidDayInputTest1(){

        try {
            DateProperties dateProperties = ValidateDate.parse("~2 03 2005");
        } catch (ConvertDataException ex){
           Assert.assertEquals("Invalid value of DD, MM or YYYY",ex.getMessage());
        }
    }

    @Test
    public void corruptDataInputTest1(){

        try {
            DateProperties dateProperties = ValidateDate.parse("03 2005");
        } catch (ConvertDataException ex){
            Assert.assertEquals("Invalid format found",ex.getMessage());
        }
    }

    @Test
    public void invalidDayInputTest2(){

        try {
            DateProperties dateProperties = ValidateDate.parse("32 03 2005");
        } catch (ConvertDataException ex){
            Assert.assertEquals("Invalid value of day",ex.getMessage());
        }
    }

    @Test
    public void invalidMonthInputTest1(){

        try {
            DateProperties dateProperties = ValidateDate.parse("02 `3 2005");
        } catch (ConvertDataException ex){
            Assert.assertEquals("Invalid value of DD, MM or YYYY",ex.getMessage());
        }
    }

    @Test
    public void invalidMonthInputTest2(){

        try {
            DateProperties dateProperties = ValidateDate.parse("02 15 2005");
        } catch (ConvertDataException ex){
            Assert.assertEquals("Invalid value of month",ex.getMessage());
        }
    }

    @Test
    public void invalidYearInputTest1(){

        try {
            DateProperties dateProperties = ValidateDate.parse("02 12 1899");
        } catch (ConvertDataException ex){
            Assert.assertEquals("Unsupported value of year",ex.getMessage());
        }
    }

    @Test
    public void leapYearValidInputTest1(){

        try {
            DateProperties dateProperties = ValidateDate.parse("29 02 2008");

            Assert.assertEquals(29, dateProperties.getDayValue());
            Assert.assertEquals(2, dateProperties.getMonthValue());
            Assert.assertEquals(2008, dateProperties.getYearValue());

        } catch (Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void leapYearValidInputTest2(){

        try {
            DateProperties dateProperties = ValidateDate.parse("29 02 2004");

            Assert.assertEquals(29, dateProperties.getDayValue());
            Assert.assertEquals(2, dateProperties.getMonthValue());
            Assert.assertEquals(2004, dateProperties.getYearValue());

        } catch (Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void leapYearInvalidInputTest1(){

        try {
            DateProperties dateProperties = ValidateDate.parse("29 02 1961");

        } catch (ConvertDataException ex){
            Assert.assertEquals("Invalid value of day", ex.getMessage());
        }
    }
}
