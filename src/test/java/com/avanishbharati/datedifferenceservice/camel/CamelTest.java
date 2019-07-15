package com.avanishbharati.datedifferenceservice.camel;


import com.avanishbharati.datedifferenceservice.model.ServiceInput;
import com.avanishbharati.datedifferenceservice.model.ServiceOutput;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestClientException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CamelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelTest.class);

    private static final int MAX_ITERATION = 1000;


    @LocalServerPort
    private String port;

    @Autowired
    protected CamelContext context;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url;

    @Before
    public void setUp(){

        url = "http://localhost:" + port + "/datevalidationservice/api/v1/datediff";

    }


    @Test
    public void TestRandomDateSuccess(){

       String earliestDate =  getRandomDates(1900);
       String latestDate = getRandomDates(2010);

       //String earliestDate =  "01 02 2009";
        //String latestDate = "01 01 2010";

        for (int i = 0; i < MAX_ITERATION; i++) {

            try {
                Long sdkDateDiff = getSDKDateDiff(earliestDate, latestDate);

                LOGGER.info("SDK date difference {} - {} = {}", earliestDate,latestDate, sdkDateDiff );

                ServiceInput serviceInput = new ServiceInput();
                serviceInput.setEarliestDate(earliestDate);
                serviceInput.setLatestDate(latestDate);

                ServiceOutput serviceOutput = sendRequest(serviceInput);

                LOGGER.info("Service date difference latest {} - earliest {} = {}", serviceOutput.getLatestDate(), serviceOutput.getLatestDate(), serviceOutput.getDifference() );

                Assert.assertEquals(sdkDateDiff.toString(), String.valueOf(serviceOutput.getDifference()));


            } catch (Exception ex){
                LOGGER.error("Conversation error",ex);
                Assert.fail();
            }
        }

    }

    private String getRandomDates(int year){

        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("Australia/Queensland");
        calendar.setTimeZone(timeZone);
        //int year = randBetween(1900, 2010);
        calendar.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1,calendar.getActualMaximum(calendar.DAY_OF_YEAR));
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        Date date = calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        formatter.setTimeZone(timeZone);

        LOGGER.info("Random date: {}", formatter.format(date));

        return  formatter.format(date);

    }

    private long getSDKDateDiff(String earliestDate, String latestDate) throws ParseException{

        Date earliest = convertToDate( earliestDate);
        Date latest = convertToDate(latestDate);

        long diffMills = (latest.getTime() - earliest.getTime());
        long diff = TimeUnit.DAYS.convert(diffMills, TimeUnit.MILLISECONDS);

        return diff;
    }

    private Date convertToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        return formatter.parse(date);
    }


    private ServiceOutput sendRequest( ServiceInput request) throws RestClientException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(Exchange.CONTENT_TYPE, "application/json");

        HttpEntity<ServiceInput> httpEntity = new HttpEntity<>(request, httpHeaders);

        ResponseEntity<ServiceOutput> response;

        response = testRestTemplate.postForEntity(url,httpEntity, ServiceOutput.class);

        LOGGER.info("Return from endpoint {}", response.getBody());

        return response.getBody();

    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
