package com.solvd.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestRunnable implements Runnable{
    private static final Logger log = LogManager.getLogger(TestRunnable.class);
    @Override
    public void run(){
        log.info("I´m a runnable thread");
    }
}