package com.solvd.hospital;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    Properties properties= new Properties();
    public static final int MAX_POOL_SIZE = 5;

    private static ConnectionPool dataSource;
    private BasicDataSource basicDataSource;

    private ConnectionPool(){
        try {
            properties.load(new FileInputStream(new File("src/main/resources/configuration.properties")));
        } catch (IOException e){
            log.error(e);
        }
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(properties.getProperty("DRIVER"));
        basicDataSource.setUsername(properties.getProperty("USER"));
        basicDataSource.setPassword(properties.getProperty("PASS"));
        basicDataSource.setUrl(properties.getProperty("URL"));

        basicDataSource.setMinIdle(1);
        basicDataSource.setMaxIdle(MAX_POOL_SIZE);
        basicDataSource.setMaxTotal(MAX_POOL_SIZE);
        basicDataSource.setMaxWaitMillis(-1);

    }

    synchronized public static ConnectionPool getInstance() {
        if (dataSource == null) {
            dataSource = new ConnectionPool();
        }
        return dataSource;
    }

    private Connection getConnection() throws SQLException{
        return this.basicDataSource.getConnection();
    }

    synchronized public Connection openAndPoolConnection() throws SQLException {

        while (basicDataSource.getNumActive() == MAX_POOL_SIZE){
            try {
                log.info("I´m waiting till a connection is available");
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        Connection connection = dataSource.getConnection();
        log.info("There are: " + basicDataSource.getNumIdle() + " idle connections");
        log.info("There are: " + basicDataSource.getNumActive() + " active connections");
        notifyAll();
        return connection;
    }

    synchronized public void closeConnection(Connection connection) throws SQLException {
        connection.close();
        notifyAll();
    }
    public void closeAllIdleConnections () {
        log.info("There are: " + basicDataSource.getNumIdle() + " idle connections");
        log.info("There are: " + basicDataSource.getNumActive() + " active connections");
        try {
            basicDataSource.close();
        } catch (SQLException e) {
            log.error(e);
        }
        log.info("All connections were released");
    }
}
