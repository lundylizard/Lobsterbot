package de.lukkyz.lobsterbot.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LobsterDatabase {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/lobsterbot";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
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

        String end = "";

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM `persons` ORDER BY `persons`.`month` ASC, `persons`.`day` ASC");

            while (results.next()) {

                String id = results.getString("discord_id");
                int day = results.getInt("day");
                int month = results.getInt("month");
                final String[] month_name = {"Janurary", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                String name = results.getString("name");

                end += "**" + name.toUpperCase() + "** -- " + day + ". " + month_name[month - 1] + "\n";

            }

            results.close();
            statement.close();
            disconnect();

            return end.substring(0, end.length() - 1);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertBday(long id, int day, int month, String name) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into persons values (" + id + ", " + month + ", " + day + ", \"" + name + "\")");
            statement.close();
            disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteBday(String name) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from persons where name = \"" + name + "\"");
            statement.close();
            disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean blacklistedUser(long id) {

        List<Long> banned = new ArrayList<>();

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from blacklisted_users");

            while (results.next()) {
                banned.add(results.getLong("id"));
            }

            return banned.contains(id);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            banned.clear();
        }

        return false;

    }

    public String getBotToken() {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from bot_token");

            while (results.next()) {

                return results.getString("token");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";

    }

}
