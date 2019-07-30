package com.avanishbharati.datedifferenceservice.route;

import com.avanishbharati.datedifferenceservice.model.ServiceRequest;
import com.avanishbharati.datedifferenceservice.model.ServiceResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RestAPIRoute extends RouteBuilder {

    private static final String LOG_NAME = "MainRESTRoute";

    @Value("${api.ver}")
    private String apiVersion;

    @Value("${server.context-path}")
    String contextPath;

    @Value("${server.port}")
    String serverPort;

    @Override
    public void configure() throws Exception {


        //Common Rest config
        restConfiguration()
            .component("servlet")
            .contextPath(contextPath+"/api/v1").host("localhost").port(serverPort)
            .enableCORS(true)
            .bindingMode(RestBindingMode.json)

            //Enable swagger endpoint.
            .apiContextPath("/api-doc") //swagger endpoint path
            .apiContextRouteId("swagger") //id of route providing the swagger endpoint

            //Swagger properties
            .apiProperty("cors","true")
            .apiProperty("api.title", "Date Difference Service API")
            .apiProperty("schemes","http")
            .apiProperty("api.version", "1.0");


        rest("/datediff").id("date-diff-api").description("Date Difference Service API")
            .post().description("Get days between given dates")
            .consumes("application/json")
            .type(ServiceRequest.class)
            .produces("application/json")
            .bindingMode(RestBindingMode.json)
            .outType(ServiceResponse.class)
            .to("direct:getDateDiff");

    }
}
