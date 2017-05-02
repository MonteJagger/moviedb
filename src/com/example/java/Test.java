package com.example.java;

import java.sql.*;

/**
 * Created by Hiumathy on 4/28/17.
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        // 1. get a connection
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys" + "?user=root&password=2010rockS&useSSL=false");

        // 2. create a statement
        Statement myStmt = myConn.createStatement();

        // 3. create queries
        // Query 1. Titles of movies that are out this week
        ResultSet outThisWeek = myStmt.executeQuery("SELECT Title, Release_Date \n" +
                "FROM MOVIE\n" +
                "WHERE Release_Date BETWEEN CURDATE() AND (CURDATE() + 7)");





        // 4. process result set (CUSTOMERS) (might need to put in class)
        System.out.println("Titles of movies that are out this week");
        System.out.printf("%-15s %-15s\n", "Title", "Date Released");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        while (outThisWeek.next()) {
            System.out.printf("%-15s %-15s\n", outThisWeek.getString("Title"), outThisWeek.getDate("Release_Date"));
        }
    }
}
