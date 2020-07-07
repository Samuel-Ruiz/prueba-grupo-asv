package com.grupoasv.prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PruebaApplication{

    public static void main(String[] args){
        SpringApplication.run(PruebaApplication.class, args);
    }

}
