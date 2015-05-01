package com.oracle.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author pkalvani
 */
public class Results {
    public Ride execute(int UserID) throws SQLException {

        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;
        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@adc2140577.us.oracle.com:1521:sdaas1", "MUCHO_VERDE",
                    "savetheplanet");
        }
        catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        }
        else {
            System.out.println("Failed to make connection!");
        }

        System.out.println("-------- Oracle PL/SQL Code Testing ------");
        Statement statement = connection.createStatement();

        //Check if ride exists
        Ride r;
        ResultSet result = statement.executeQuery("SELECT * FROM "
                + "RIDE_PASSENGER WHERE USER_ID = " + UserID
                + "AND EST_REACH_TIME < SYSTIMESTAMP(0)"
                + "AND ROWNUM = 1 ORDER BY EST_REACH_TIME ASC" );

        //If user does not exist create user
        if (!result.next()){
            System.out.println("-NO Future Ride-");
        }
        else {
            System.out.println("-Future Ride-");

            ResultSet ride_main = statement.executeQuery("SELECT "
                    + "DRIVER_USER_ID, GALLONS_SAVED FROM RIDE WHERE RIDE_ID = "
                    + Integer.parseInt(result.getString(0)));

            r = new Ride(
                    result.getString(0), //rideID
                    result.getString(1), //userID
                    result.getString(4), //address
                    ride_main.getString(0),           //driverID
                    Integer.parseInt(result.getString(2)), //order
                    java.sql.Timestamp.valueOf(result.getString(7)).getTime(),
                    java.sql.Timestamp.valueOf(result.getString(8)).getTime(),
                    ride_main.getDouble(1) //fuel_saved
            );


            //UNCOMMENT THIS!
            return r;
        }

        return null;
    }
}
