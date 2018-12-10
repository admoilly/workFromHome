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
    static ResultSet resultSetObject =null;
    static Connection connectionObject = null;
    static PreparedStatement preparedStatement = null;
    static ConnectionPool jdbcConnectionPool = new ConnectionPool();
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

    private static ResultSet resultSet(String sqlQuery){
        try {
            DataSource dataSource = jdbcConnectionPool.setUpPool();
            connectionObject = dataSource.getConnection();
            preparedStatement = connectionObject.prepareStatement(sqlQuery);
            resultSetObject = preparedStatement.executeQuery();
        } catch(Exception ignored) {}
        return resultSetObject;
    }

    public static ResultSet  readData(String driver,String dburl,String user,String pass,String sqlQuery) throws SQLException {
        JDBC_DB_URL=dburl;
        JDBC_DRIVER=driver;
        JDBC_USER=user;
        JDBC_PASS=pass;
        return resultSet(sqlQuery);
    }
}
