package com.example.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.annotation.Validated;

import java.util.TimeZone;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@SpringBootApplication
//@ComponentScan({"com.example.fooddelivery.services"})
//@EntityScan("com.example.fooddelivery.entities")
@EnableJpaRepositories("com.example.fooddelivery.repositories")
public class FoodDeliveryApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(FoodDeliveryApplication.class);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(FoodDeliveryApplication.class, args);
    }

}