package de.lukkyz.lobsterbot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    public String getBdays(MessageReceivedEvent event) {

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

                end += event.getGuild().getMemberById(id).getAsMention() + " -- " + day + ". " + month_name[month - 1] + "\n";

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

    public void insertBday(String id, int day, int month, String name) {

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

    public void deleteBday(String id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from persons where discord_id = \"" + id + "\"");
            statement.close();
            disconnect();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public boolean isInBdaysDatabase(String id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select discord_id from persons");
            List<String> ids = new ArrayList<>();

            while (results.next()) {

                ids.add(results.getString("discord_id"));

            }

            return ids.contains(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

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

    public void addStarsToUser(String id, int amount) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update stars set amount = " + amount + " where id = \"" + id + "\"");
            statement.close();
            disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeStarsFromUser(String id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update stars set amount = 0 where id = \"" + id + "\"");
            statement.close();
            disconnect();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public int getStarsFromUser(String id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from stars");
            HashMap<String, Integer> stars = new HashMap<>();
            String userid = "";

            while (results.next()) {

                userid = results.getString("id");
                int amount = results.getInt("amount");
                stars.put(userid, amount);

            }

            return stars.get(userid);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1;

    }

    public void createStarDatabase(String id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into stars values (\"" + id + "\", 0)");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public List<String> getLeaderboard(MessageReceivedEvent event) {

        List<String> leaderboard = new ArrayList<>();
        int rank = 0;

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from stars order by `stars`.`amount` DESC");

            while (results.next()) {

                rank++;
                leaderboard.add(rank + ") **" + event.getJDA().getUserById(results.getString("id")).getName().toUpperCase() + "** -- " + results.getInt("amount") + " :star:");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return leaderboard;

    }

    public boolean isInStarsLeaderboard(String id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select id from stars");
            List<String> ids = new ArrayList<>();

            while (results.next()) {

                ids.add(results.getString("id"));

            }

            return ids.contains(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public int getEXPfromUser(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeQuery("");

        } catch (SQLException e) {

            e.printStackTrace();
            return -1;

        }

        return 0;

    }

    public void addEXPToUser(Member member, int amount) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}
