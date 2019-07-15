package com.avanishbharati.datedifferenceservice.route;

import com.avanishbharati.datedifferenceservice.model.ServiceInput;
import com.avanishbharati.datedifferenceservice.model.ServiceOutput;
import com.avanishbharati.datedifferenceservice.processor.DataProcessor;
import com.avanishbharati.datedifferenceservice.processor.ExceptionProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateDiffRESTRoute extends RouteBuilder {


    private static final String LOG_NAME = DateDiffRESTRoute.class.getName();

    @Autowired
    DataProcessor dataProcessor;

    @Autowired
    ExceptionProcessor exceptionProcessor;

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
            .process(exceptionProcessor)
            .handled(true);

        from("direct:getDateDiff").routeId("getDateDiffRouteId")
            .log(LoggingLevel.INFO, LOG_NAME, "Rest Data \n ${body} and headers are \n ${headers}")
            .setProperty("restRoute").simple("TRUE")
            .process(dataProcessor).id("restDataProcessorId")
            .log(LoggingLevel.INFO, LOG_NAME, "Output is \n ${body}");
    }

}
