package com.example.java;
import javax.swing.*;
import java.sql.*;

public class Drive {

    public static Connection Connect(){

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/movie_db" +
                    "?user=root&password=lexanne23&useSSL=false");
            return conn;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            // 1. get a connection
            Drive myConn = new Drive();
            Statement myStmt = myConn.Connect().createStatement();
            // 2. create a statement
//            Statement myStmt = myConn.createStatement();

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

            // Query 2. Available movie times for 'Get Out' movie
            String G = "Get Out";
            ResultSet movieTimes = myStmt.executeQuery("SELECT Theater_Name, Location, Title, Movie_Time\n" +
                    "FROM THEATRE NATURAL JOIN (SELECT * FROM SHOWS NATURAL JOIN " +
                    "MOVIE WHERE Title = '" + G + "'" + ") AS G ORDER BY Movie_Time");

        /*    PreparedStatement stmt = myConn.Connect().prepareStatement();
            stmt.setString(1, poster);
            ResultSet rs = stmt.executeQuery();*/

            System.out.println("\n\n");
            System.out.println("Available movie times for 'Get Out' movie");
            System.out.printf("%-25s |%-30s |%-20s |%-15s\n", "Theater Name", "Location", "Title", "Show Time");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            while (movieTimes.next()) {
                System.out.printf("%-25s |%-30s |%-20s |%-15s\n", movieTimes.getString("Theater_Name"), movieTimes.getString("Location"), movieTimes.getString("Title"), movieTimes.getTime("Movie_Time"));
            }


            // Query 3. Theaters with movies that are rated 'PG' and have a show before 5PM
            ResultSet pgBefore5 = myStmt.executeQuery("SELECT Title, Movie_Time, Theater_Name, Location  \n" +
                    "FROM THEATRE NATURAL JOIN (\n" +
                    "\tSELECT *\n" +
                    "\tFROM SHOWS NATURAL JOIN MOVIE\n" +
                    ") AS t\n" +
                    "WHERE Rating = 'PG' AND Movie_Time <= '17:00:00'");

            System.out.println("\n\n");
            System.out.println("Theaters with movies that are rated 'PG' and have a show before 5PM");
            System.out.printf("%-30s |%-15s |%-30s |%-30s\n", "Movie", "Show Time", "Theater Name", "Location");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            while (pgBefore5.next()) {
                System.out.printf("%-30s |%-15s |%-30s |%-30s\n", pgBefore5.getString("Title"), pgBefore5.getString("Movie_Time"), pgBefore5.getString("Theater_Name"), pgBefore5.getString("Location"));
            }

            // Query 4. List of Employees at Rave Theater in Hurst TX
            ResultSet empListRave = myStmt.executeQuery("SELECT SSN, Name, Job_Type\n" +
                    "FROM THEATRE_EMPLOYEES\n" +
                    "WHERE Location = 'Hurst, Tx' AND Theater_Name = 'Rave'");

            System.out.println("\n\n");
            System.out.println("List of Employees at Rave Theater in Hurst, TX");
            System.out.printf("%-10s |%-15s |%-15s\n", "SSN", "Name", "Job Type");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            while (empListRave.next()) {
                System.out.printf("%-10d |%-15s| %-15s\n", empListRave.getInt("SSN"), empListRave.getString("Name"), empListRave.getString("Job_Type"));
            }

            // Query 5. Current movies and show times in Arlington TX theaters
            ResultSet movieShowsArl = myStmt.executeQuery("SELECT Theater_Name, Title, Movie_Time\n" +
                    "FROM MOVIE NATURAL JOIN (\n" +
                    "\tSELECT * \n" +
                    "    FROM Theatre NATURAL JOIN Shows\n" +
                    "\tWHERE Location = 'Arlington, Tx'\n" +
                    ") AS t\n" +
                    "ORDER BY Theater_Name, Title, Movie_Time");

            System.out.println("\n\n");
            System.out.println("Current movies and show times in Arlington, TX theaters");
            System.out.printf("%-30s |%-30s |%-15s\n", "Theater", "Title", "Show Time");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            while (movieShowsArl.next()) {
                System.out.printf("%-30s |%-30s |%-15s\n", movieShowsArl.getString("Theater_Name"), movieShowsArl.getString("Title"), movieShowsArl.getString("Movie_Time"));

            }

            // Query 6. Number of employees for each job type in Arlington, Tx theaters
            ResultSet numJobTypePerEmp = myStmt.executeQuery("SELECT Job_Type, COUNT(*)\n" +
                    "FROM THEATRE_EMPLOYEES\n" +
                    "WHERE Location = 'Arlington, Tx'\n" +
                    "GROUP BY Job_Type");

            System.out.println("\n\n");
            System.out.println("Number of employees for each job type in Arlington, Tx theaters");
            System.out.printf("%-15s |%-5s\n", "Job Type", "Count");
            System.out.println("-----------------------------------------------------------------------------------------------------");

            while (numJobTypePerEmp.next()) {
                System.out.printf("%-15s |%d\n", numJobTypePerEmp.getString("Job_Type"), numJobTypePerEmp.getInt("COUNT(*)"));
            }

            // Query 7. Total revenue from ticket purchases for each movie greater than $10.00 in order for greatest to least
            ResultSet movieRev10 = myStmt.executeQuery("SELECT Title, CONVERT(SUM(Price), DECIMAL(10,2)) AS Revenue\n" +
                    "FROM TICKETS\n" +
                    "GROUP BY Title\n" +
                    "HAVING Revenue > 10.00\n" +
                    "ORDER BY Revenue DESC");

            System.out.println("\n\n");
            System.out.println("Total revenue from ticket purchases for each movie greater than $10.00 in order for greatest to least");
            System.out.printf("%-30s |%-10s\n", "Movie", "Revenue");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            while (movieRev10.next()) {
                System.out.printf("%-30s |$%-10.2f\n", movieRev10.getString("Title"), movieRev10.getFloat("Revenue"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
