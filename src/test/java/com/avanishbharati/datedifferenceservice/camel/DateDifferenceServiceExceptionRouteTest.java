package com.avanishbharati.datedifferenceservice.camel;


import com.avanishbharati.datedifferenceservice.exception.ConvertDataException;
import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class DateDifferenceServiceExceptionRouteTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateDifferenceServiceExceptionRouteTest.class);

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "mock:output")
    private MockEndpoint mockEndpoint;

    @Produce(uri = "file:src/test/resources/data/out")
    private ProducerTemplate testResourceOutput;

    @Produce(uri="file:src/test/resources/data/in?fileName=badvalues.txt&noop=true")
    private ProducerTemplate producerTemplate;

    protected CamelContext createCamelContext() throws Exception{
        return camelContext;
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
    }

    @Test
    public void routeTest() throws Exception {
        try {
            camelContext.start();
            Thread.sleep(5000);
            //not body to send
            producerTemplate.start();
            camelContext.stop();

        } catch (CamelExecutionException ex){
            Assertions.assertThat(ex.getCause()).isInstanceOf(ConvertDataException.class);
        } catch ( Exception ex){
            Assertions.fail(ex.getMessage());
        }
    }
}
