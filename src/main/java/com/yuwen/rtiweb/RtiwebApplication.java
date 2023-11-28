package com.yuwen.rtiweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.yuwen.rtiweb.mapper")
public class RtiwebApplication {

    public static void main(String[] args) {
        System.out.println("RtiwebApplication start");
        SpringApplication.run(RtiwebApplication.class, args);
    }

}
