package com.avanishbharati.datedifferenceservice.processor;

import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import com.avanishbharati.datedifferenceservice.model.ServiceRequest;
import com.avanishbharati.datedifferenceservice.model.ServiceResponse;
import com.avanishbharati.datedifferenceservice.service.DateDifferenceCalculator;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.UUID;

@Component
public class DataProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessor.class);

    private static final int NUM_FIELDS = 2;
    private static final String DATA_SEPARATOR = ",";

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info("DataProcessor::process() - START");

        String breadcrumbID = exchange.getIn().getHeader(Exchange.BREADCRUMB_ID, String.class);
        setBreadcrumbID(breadcrumbID);

        LOGGER.info("Request breadcrumbID from exchange : {}", breadcrumbID);

        String body = exchange.getIn().getBody(String.class);

        LOGGER.info("Data from exchange : {}", body);


        if ("TRUE".equals(exchange.getProperty("restRoute"))){
            if (exchange.getIn().getBody().getClass().equals(ServiceRequest.class)) {

                ServiceRequest serviceRequest = exchange.getIn().getBody(ServiceRequest.class);
                ServiceResponse serviceResponse = processDataValues(serviceRequest.getEarliestDate(), serviceRequest.getLatestDate());

                exchange.getIn().setBody(serviceResponse);

            } else {
                LOGGER.error("Invalid request");
                throw new IllegalArgumentException();
            }
        } else {


            String readLine = null;
            String newValue = "";

            GenericFile<File> file = (GenericFile<File>) exchange.getIn().getBody();

            if (file != null) {

                FileReader file1 = new FileReader(file.getFile());

                BufferedReader reader = new BufferedReader(file1);

                while ((readLine = reader.readLine()) != null) {

                    String[] data = StringUtils.split(readLine.trim(), DATA_SEPARATOR);


                    if (data.length != NUM_FIELDS) {
                        LOGGER.error("Invalid data was found : {}", body);
                        throw new ConvertDataException("Invalid data");
                    }

                    data[0] = org.apache.commons.lang3.StringUtils.normalizeSpace(data[0]);
                    data[1] = org.apache.commons.lang3.StringUtils.normalizeSpace(data[1]);

                    String oldValue = transform(processDataValues(data[0], data[1]));

                    LOGGER.info("Processed data line output: {}", oldValue);

                    newValue = newValue.concat(oldValue).concat("\n");

                }

                LOGGER.info("Processed data line output: {}", newValue);
                exchange.getIn().setBody(newValue);
            }

        }
        LOGGER.info("DataProcessor::process() - END");
    }

    private String transform(ServiceResponse serviceResponse) {
        return serviceResponse.getEarliestDate() + ", " + serviceResponse.getLatestDate() + ", " + serviceResponse.getDifference();
    }

    private ServiceResponse processDataValues(String earliestDate, String latestDate) throws ConvertDataException {

        LOGGER.info("earliestDate : {}, latestDate : {}", earliestDate, latestDate);

        int dateDifference = DateDifferenceCalculator.dateDifference(earliestDate, latestDate);

        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setDifference(dateDifference);
        serviceResponse.setEarliestDate(earliestDate);
        serviceResponse.setLatestDate(latestDate);

        LOGGER.info("processDataValues : {}", serviceResponse);

        return serviceResponse;
    }

    private void setBreadcrumbID(String breadcrumbID){

        if(org.apache.commons.lang3.StringUtils.isEmpty(breadcrumbID)){
            breadcrumbID = UUID.randomUUID().toString();
        }

        MDC.put("camel.breadcrumbId", breadcrumbID);
    }

}
