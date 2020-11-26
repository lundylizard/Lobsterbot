package de.lukkyz.lobsterbot.utils;

import java.sql.*;
import java.util.Properties;

public class LobsterDatabase {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/lobsterbot";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    String end = "";
    private Connection connection;
    private Properties properties;

    public Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", "250");
        }

        return properties;

    }

    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBdays() {

        try {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from persons");

            while (results.next()) {

                String id = results.getString("discord_id");
                int day = results.getInt("day");
                String month = results.getString("month");
                String name = results.getString("name");


                end += id + "," + day + "," + month + "," + name + "#";

            }

            results.close();
            statement.close();
            disconnect();

            return end.substring(0, end.length() - 1).substring(4);

        } catch (SQLException e) {
            return null;
        } finally {

            end = "";
        }
    }

}
