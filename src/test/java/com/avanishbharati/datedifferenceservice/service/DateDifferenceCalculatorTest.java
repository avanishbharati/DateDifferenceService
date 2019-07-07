package com.avanishbharati.datedifferenceservice.service;

import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import org.junit.Assert;
import org.junit.Test;

public class DateDifferenceCalculatorTest {

    @Test
    public void validInputTest(){

        int dateDifference = 0;

        dateDifference = DateDifferenceCalculator.dateDifference("19 02 2004", "25 02 2005");
        Assert.assertEquals(372, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("31 12 2009", "01 01 2010");
        Assert.assertEquals(1, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("15 06 2009", "31 12 2010");
        Assert.assertEquals(564, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("15 06 2009", "30 06 2009");
        Assert.assertEquals(15, dateDifference);
    }

    @Test
    public void validLeapYearInputTest() {
        int dateDifference = 0;

        dateDifference = DateDifferenceCalculator.dateDifference("01 06 2008", "01 07 2008");
        Assert.assertEquals(30, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("01 02 2008", "01 03 2008");
        Assert.assertEquals(29, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("01 02 2009", "01 03 2009");
        Assert.assertEquals(28, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("31 12 2007", "31 12 2008");
        Assert.assertEquals(366, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("31 12 2007", "31 12 2009");
        Assert.assertEquals(731, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("31 12 2007", "31 12 2010");
        Assert.assertEquals(1096, dateDifference);

        dateDifference = DateDifferenceCalculator.dateDifference("15 06 2009", "15 06 2009");
        Assert.assertEquals(0, dateDifference);

    }

    @Test
    public void InvalidYearInputTest() {

        int dateDifference = 0;
        try {

            dateDifference = dateDifference = DateDifferenceCalculator.dateDifference("01 06 2009", "01 03 2008");

        } catch (ConvertDataException ex) {
            Assert.assertEquals("First date should be the earliest and the second date should be the latest.", ex.getMessage());
        }

        try {
            dateDifference = dateDifference = DateDifferenceCalculator.dateDifference("01 03 2009", "01 02 2009");

        } catch (ConvertDataException ex) {
            Assert.assertEquals("First date should be the earliest and the second date should be the latest.", ex.getMessage());
        }


        try {
            dateDifference = dateDifference = DateDifferenceCalculator.dateDifference("15 03 2009", "03 03 2009");

        } catch (ConvertDataException ex) {
            Assert.assertEquals("First date should be the earliest and the second date should be the latest.", ex.getMessage());
        }

    }

}
