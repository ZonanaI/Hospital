package com.solvd.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestRunnable implements Runnable{
    private static final Logger log = LogManager.getLogger(TestRunnable.class);
    private static final int napTime = 5; //nap time in seconds
    private boolean awake;
    @Override
    public void run() {
        log.info("I´m a runnable thread");
        log.info("I'm taking a " + napTime + " seconds nap");
        awake = false;
        try {
            Thread.sleep(napTime * 1000);
        } catch (InterruptedException ignored) {}
        awake = true;
        log.info("I'm awake now!");
    }

    //Getters

    public boolean isAwake() {
        return awake;
    }
}