package com.avanishbharati.datedifferenceservice.processor;

import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import com.avanishbharati.datedifferenceservice.model.ServiceOutput;
import com.avanishbharati.datedifferenceservice.service.DateDifferenceCalculator;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Component
public class DataProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessor.class);

    private static final int NUM_FIELDS = 2;
    private static final String DATA_SEPARATOR = ",";

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.info("DataProcessor::process() - START");

        String body =  exchange.getIn().getBody(String.class);
        LOGGER.info("Data from file: {}", body);

        String readLine=null;
        String newValue ="";


        GenericFile<File> file = (GenericFile<File>) exchange.getIn().getBody();

        if(file!=null) {

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

                LOGGER.info("Processed data line output: {}", oldValue );

                newValue = newValue.concat(oldValue).concat("\n");

            }

            LOGGER.info("Processed data line output: {}", newValue );
            exchange.getIn().setBody(newValue);
        }
        LOGGER.info("DataProcessor::process() - END");
    }

    private String transform(ServiceOutput serviceOutput){
        return serviceOutput.getEarliestDate() + ", " + serviceOutput.getLatestDate() + ", " + serviceOutput.getDifference();
    }

    private ServiceOutput processDataValues( String earliestDate, String latestDate ) throws ConvertDataException{

        LOGGER.info("earliestDate : {}, latestDate : {}",earliestDate, latestDate );

        int dateDifference = DateDifferenceCalculator.dateDifference(earliestDate, latestDate);

        ServiceOutput serviceOutput = new ServiceOutput();
        serviceOutput.setDifference(dateDifference);
        serviceOutput.setEarliestDate(earliestDate);
        serviceOutput.setLatestDate(latestDate);

        LOGGER.info("processDataValues : {}", serviceOutput);

        return serviceOutput;
    }

}
