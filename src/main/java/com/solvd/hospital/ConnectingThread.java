package com.solvd.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectingThread extends Thread{
    private static final Logger log = LogManager.getLogger(ConnectingThread.class);
    private int connectedNapTime = 7;   //nap time in seconds before releasing connection
    public ConnectingThread (int napTime){
        super();
        this.connectedNapTime = napTime;
    }

    public void run () {

        try {
            Connection c = ConnectionPool.getInstance().openAndPoolConnection();
            if (c != null){
                log.info("I could get the connection time for a " + connectedNapTime + " seconds nap");
                sleep(connectedNapTime * 1000);
                log.info("Now I´ll release the connection");
                ConnectionPool.getInstance().closeConnection(c);
            }
        } catch (SQLException | InterruptedException e) {
            log.error(e);
        }
    }
}
