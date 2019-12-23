package com.gs56.wm2gs56;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan(value = {"com.gs56.wm2gs56.mapper"})
public class Wm2gs56Application {

    public static void main(String[] args) {
        SpringApplication.run(Wm2gs56Application.class, args);
    }

}
