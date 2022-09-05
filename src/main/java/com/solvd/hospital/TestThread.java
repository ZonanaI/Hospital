package com.solvd.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestThread extends Thread {
    private static final Logger log = LogManager.getLogger(TestThread.class);
    public void run (){
        log.info("I´m a thread class thread");
    }
}