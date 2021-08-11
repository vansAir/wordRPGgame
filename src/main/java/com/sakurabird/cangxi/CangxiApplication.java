package com.sakurabird.cangxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@MapperScan("com.sakurabird.cangxi.dao")
@SpringBootApplication
@EnableScheduling
public class CangxiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CangxiApplication.class, args);
    }

}
