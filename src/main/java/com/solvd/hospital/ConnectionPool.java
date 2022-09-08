package com.solvd.hospital;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private final String DB="nacimientos";
    private final String URL="jdbc:mysql://localhost:3306/"+DB+"?useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USER="root";
    private final String PASS="123654789";

    public static final int MAX_POOL_SIZE = 5;

    private static ConnectionPool dataSource;
    private BasicDataSource basicDataSource;

    private ConnectionPool(){

        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASS);
        basicDataSource.setUrl(URL);

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
