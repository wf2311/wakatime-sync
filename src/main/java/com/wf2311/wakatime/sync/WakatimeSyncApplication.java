package com.wf2311.wakatime.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
//@EntityScan(basePackageClasses =
//        {WakatimeSyncApplication.class,
//                Jsr310JpaConverters.class})
public class WakatimeSyncApplication {

    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        SpringApplication.run(WakatimeSyncApplication.class, args);
    }

}

