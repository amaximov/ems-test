package com.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AppRunner {
    public static void main(String[] args) {
        ApplicationContext ctx = new GenericXmlApplicationContext("spring-listener.xml");
        System.out.println("context started successfully: " + ctx);
    }
}
