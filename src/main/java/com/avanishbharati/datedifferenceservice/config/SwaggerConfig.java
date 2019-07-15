package com.avanishbharati.datedifferenceservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class SwaggerConfig {

    @Value("${server.context-path}")
    String contextPath;

    @Controller
    class SwaggerWelcome {
        @RequestMapping("/swagger-ui")
        public String redirectToUi() {
            return "redirect:/webjars/swagger-ui/index.html?url="+ contextPath + "/api/v1/api-doc&validatorUrl=";
        }
    }
}
