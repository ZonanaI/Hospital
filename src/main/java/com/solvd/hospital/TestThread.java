package com.solvd.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestThread extends Thread {
    private static final Logger log = LogManager.getLogger(TestThread.class);
    private int napTime = 7; //nap time in seconds

    public void run () {
        log.info("I´m a thread class thread");
        log.info("I'm taking a " + napTime + " seconds nap");
        try {
            Thread.sleep(napTime * 1000);
        } catch (InterruptedException ignored) {}
        log.info("I'm awake now!");
    }

}