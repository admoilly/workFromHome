package com.jcg.jdbc.connection.pooling;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amit Sharma on 12/11/2018.
 */
public class dbImpl extends ConnectionPool{
    public static void main(String[] args) throws SQLException {
        ResultSet rs=readData("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/world?useSSL=false",
                "root",
                "root",
                "select distinct District from city where CountryCode='IND'");
        jdbcConnectionPool.printDbStatus();
        while (rs.next()){
            System.out.println(rs.getString("District"));
        }
        resultSetObject.close();
        connectionObject.close();
        preparedStatement.close();
        jdbcConnectionPool.printDbStatus();
    }
}
