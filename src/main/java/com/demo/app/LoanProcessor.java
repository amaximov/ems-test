package com.demo.app;

import com.demo.model.Loan;

import java.util.Date;

/**
 * business logic to process incoming message; messaging infrastructure calls this first; the entry point for incoming messages
 */
public class LoanProcessor {
    public String processLoan(Loan loan) {
        long thread = Thread.currentThread().getId();
        long time = System.currentTimeMillis();
        System.out.println(thread + " " + time + ": loan = " + loan);

        if (thread % 2 == 0) {
            System.out.println("sleeping in " + thread);
            sleep(5000);
        }

        return "success: " + loan.getId();
    }

    private void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
