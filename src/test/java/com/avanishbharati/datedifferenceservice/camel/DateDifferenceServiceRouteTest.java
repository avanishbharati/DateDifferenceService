package com.avanishbharati.datedifferenceservice.camel;


import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class DateDifferenceServiceRouteTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateDifferenceServiceRouteTest.class);

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "mock:output")
    private MockEndpoint mockEndpoint;

    @Produce(uri = "file:src/test/resources/data/out")
    private ProducerTemplate testResourceOutput;

    @Produce(uri="file:src/test/resources/data/in?fileName=sample.txt&noop=true")
    private ProducerTemplate producerTemplate;

    protected CamelContext createCamelContext() throws Exception{
        return camelContext;
    }

    @Override
    public boolean isUseAdviceWith(){
        return true;
    }


    @Before
    public void setUp() throws Exception {

        LOGGER.info("Setup route");

        camelContext.getRouteDefinition("fileInputRouteId")
            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    replaceFromWith(producerTemplate.getDefaultEndpoint().getEndpointUri());
                }
            });

        camelContext.getRouteDefinition("fileInputRouteId")
            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    weaveById("OutputRouteId").replace().to(testResourceOutput.getDefaultEndpoint()).id("OutputRouteId");
                }
            });

        camelContext.getRouteDefinition("fileInputRouteId")
            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    this.weaveAddLast().to(mockEndpoint.getEndpointUri());
                }
            });
    }

    @Test
    public void routeTest() throws Exception {

        camelContext.start();
        Thread.sleep(5000);
        //not body to send
        producerTemplate.start();
        camelContext.stop();

        String response;
        for (Exchange exchange : mockEndpoint.getExchanges()) {
            response = exchange.getIn().getBody(String.class);
            LOGGER.info("Processes output {}", response);
        }

        String validResult=null;
        try {
            validResult = getTestData("data/valid/sample.txt");
        } catch (Exception e) {
            Assert.fail("valid compare file missing");
        }

        mockEndpoint.expectedBodiesReceived(validResult);

    }


    private String getTestData(String fileName){
        try{
            File file = ResourceUtils.getFile("classpath:"+ fileName);
            return new String(Files.readAllBytes(file.toPath()));

        }catch (IOException ex){
            throw new RuntimeException();
        }
    }
}
