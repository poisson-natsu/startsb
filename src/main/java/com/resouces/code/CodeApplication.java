package com.resouces.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class CodeApplication {

    public static void main(String[] args) {
//        SpringServletContainerInitializer
        SpringApplication.run(CodeApplication.class, args);
    }

}
