package com.demo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SenderRunner {
    public static void main(String[] args) {
        ApplicationContext ctx = new GenericXmlApplicationContext("spring-sender.xml");
        Sender sender = ctx.getBean(Sender.class);

        System.out.println("sending messages");

        for (int i = 0; i < 1000; i++) {
            String message = String.format("id=%s;value=%s", i, i * 123.3);

            String compressibleAppendix = fluff();

            sender.send(message + ";" + compressibleAppendix);
        }

        System.out.println("done sending messages");
    }

    private static String fluff() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }

        return sb.toString();
    }
}
