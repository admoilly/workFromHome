package com.jcg.jdbc.connection.pooling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionPool {

    static String JDBC_DRIVER =null;
    static String JDBC_DB_URL =null;
    static ResultSet rsObj=null;
    static Connection connObj = null;
    static PreparedStatement pstmtObj = null;
    static ConnectionPool jdbcObj = new ConnectionPool();
    static String JDBC_USER = null;
    static String JDBC_PASS = null;

    private static GenericObjectPool gPool = null;

    @SuppressWarnings("unused")
    private DataSource setUpPool() throws Exception {
        Class.forName(JDBC_DRIVER);
        gPool = new GenericObjectPool();
        gPool.setMaxActive(5);
        ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    private GenericObjectPool getConnectionPool() {
        return gPool;
    }

    public void printDbStatus() {
        System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
    }

    public static ResultSet resultSetReadData(String sqlQuery){
        try {
            DataSource dataSource = jdbcObj.setUpPool();
            connObj = dataSource.getConnection();
            jdbcObj.printDbStatus();
            pstmtObj = connObj.prepareStatement(sqlQuery);
            rsObj = pstmtObj.executeQuery();
        } catch(Exception ignored) {}
        return rsObj;
    }
}
