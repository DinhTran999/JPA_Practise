package com.axonactive.jpa.config;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ResourceConfig extends Application {
    public ResourceConfig() {
        init();
    }
    private void init() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/JPA-1.0-SNAPSHOT/api");
        beanConfig.setResourcePackage("com.axonactive.jpa");
        beanConfig.setTitle("RESTEasy, Swagger and Swagger UI Example");
        beanConfig.setDescription("Sample RESTful API built using RESTEasy, Swagger and Swagger UI");
                beanConfig.setScan(true);
    }
}
