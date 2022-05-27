package de.qaware.springbootweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

@SpringBootApplication
public class SpringBootWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWeatherApplication.class, args);
    }
}
