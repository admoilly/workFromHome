package com.jcg.jdbc.connection.pooling;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.jcg.jdbc.connection.pooling.ConnectionPool.resultSetReadData;

/**
 * Created by Amit Sharma on 12/11/2018.
 */
public class dbImpl {
    public static void main(String[] args) throws SQLException {
        ConnectionPool.JDBC_DB_URL="jdbc:mysql://localhost:3306/world?useSSL=false";
        ConnectionPool.JDBC_DRIVER="com.mysql.jdbc.Driver";
        ConnectionPool.JDBC_USER="root";
        ConnectionPool.JDBC_PASS="root";
        ResultSet rs=resultSetReadData("select top 10 * from City");
        while (rs.next()){
            System.out.println(rs.getString("Name"));
        }
        ConnectionPool.rsObj.close();
        ConnectionPool.connObj.close();
        ConnectionPool.pstmtObj.close();
        ConnectionPool.jdbcObj.printDbStatus();
    }
}
