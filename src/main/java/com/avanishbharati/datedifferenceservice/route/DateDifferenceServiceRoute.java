package com.avanishbharati.datedifferenceservice.route;


import com.avanishbharati.datedifferenceservice.processor.DataProcessor;
import com.avanishbharati.datedifferenceservice.processor.ExceptionProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateDifferenceServiceRoute  extends RouteBuilder {

    private static final String LOG_NAME = DateDifferenceServiceRoute.class.getName();

    @Autowired
    DataProcessor dataProcessor;

    @Autowired
    ExceptionProcessor exceptionProcessor;

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
            .process(exceptionProcessor)
            .handled(true);


        from("file:data/in?fileName=sample.txt&noop=true").routeId("fileInputRouteId")
            .log(LoggingLevel.INFO, LOG_NAME, "Read file is \n ${body} and headers are \n ${headers}")
            .process(dataProcessor).id("dataProcessorId")
            .to("file:data/out").id("OutputRouteId");
    }
}
