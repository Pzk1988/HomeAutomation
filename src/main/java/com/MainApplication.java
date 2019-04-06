package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
    private static Thread homeThread;

    public static void main(String[] args) {
        System.out.println("Home automation v. 1.0.0");
        homeThread = new Thread(new HomeAutomation());
        homeThread.start();

        SpringApplication.run(MainApplication.class, args);

    }
}
