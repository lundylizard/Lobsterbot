package de.lukkyz.lobsterbot.utils;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.Secrets;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class LobsterDatabase {

    private static final String DATABASE_URL = Secrets.DATABASE_URL;
    private static final String USERNAME = "root";
    private static final String PASSWORD = Lobsterbot.DEBUG ? "" : Secrets.PASSWORD_DATABASE;

    public static Connection connection;
    private static Properties properties;

    private static Properties getProperties() {

        if (properties == null) {

            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", "250");

        }

        return properties;

    }

    public static void connect() {

        if (connection == null) {

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());

            } catch (ClassNotFoundException | SQLException e) {

                e.printStackTrace();

            }

        }

    }

    private void disconnect() {

        if (connection != null) {

            try {

                connection.close();
                connection = null;

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    /* Birthday Database Management */

    public String getBirthdays(MessageReceivedEvent event) {

        String end = "";

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM birthdays ORDER BY birthdays.month ASC, birthdays.day ASC");

            while (results.next()) {

                long id = results.getLong("discord_id");
                int day = results.getInt("day");
                int month = results.getInt("month");
                final String[] month_name = {"Janurary", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

                /* credit to mcfluff */
                String suffix = "";
                if (day % 10 == 1 && day != 11) suffix = "st";
                if (day % 10 == 2) suffix = "nd";
                if (day % 10 == 3) suffix = "rd";
                if (day > 3 && day < 20 || day > 23 && day < 30) suffix = "th";

                Member member = event.getGuild().getMemberById(id);

                if (member != null)
                    end += member.getAsMention() + " -- " + day + suffix + " " + month_name[month - 1] + "\n";

            }

            results.close();
            statement.close();
            disconnect();

            return end.substring(0, end.length() - 1);

        } catch (SQLException e) {

            e.printStackTrace();
            return e.getCause().getMessage();

        }

    }

    public void insertBirthday(@NotNull Member member, int day, int month) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into birthdays values (" + member.getIdLong() + ", \"" + member.getUser().getName() + "\", " + month + ", " + day + ")");
            statement.close();
            disconnect();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void deleteBirthday(@NotNull String name) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from birthdays where name = \"" + name + "\"");
            statement.close();
            disconnect();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public boolean isInBirthdayDatabase(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select discord_id from birthdays");
            List<Long> ids = new ArrayList<>();

            while (results.next()) {
                ids.add(results.getLong("discord_id"));
            }

            return ids.contains(member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }

    /* Blacklisted Users */

    public boolean getUserBlacklisted(long id) {

        List<Long> banned = new ArrayList<>();

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from blacklisted_users");

            while (results.next()) {

                banned.add(results.getLong("discord_id"));

            }

            return banned.contains(id);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public void setUserBlacklisted(long id, boolean state, String name) {

        if (state) {

            try {

                connect();
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into blacklisted_users values (" + id + ", " + name + ")");
                statement.close();
                disconnect();

            } catch (SQLException e) {

                e.printStackTrace();

            }

        } else {

            try {

                connect();
                Statement statement = connection.createStatement();
                statement.executeUpdate("delete from blacklisted_users where discord_id = " + id);
                statement.close();
                disconnect();

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    /* Bot Database Management */

    public String getBotToken() {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from bot");

            while (results.next()) {

                return results.getString("token");

            }


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return "";

    }

    public int getExecutedCommandsAmount() {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from bot");

            while (results.next()) {

                return results.getInt("executed_commands");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    public void setExecutedCommandsAmount(int amount) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update bot set executed_commands = " + amount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getMessagesSentAmount() {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from bot");

            while (results.next()) {

                return results.getInt("messages_sent");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;

    }

    public void setMessagesSentAmount(int amount) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update bot set messages_sent = " + amount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /* EXP Database Management */

    public void createEXPDBEntry(@NotNull Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into exp values (" + member.getIdLong() + ", 0, 1, 0, \"" + member.getUser().getName() + "\")");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public boolean isInEXPDB(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from exp");
            List<Long> ids = new ArrayList<>();

            while (results.next()) {

                ids.add(results.getLong("discord_id"));

            }

            return ids.contains(member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public int getEXPfromUser(Member member) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("select * from exp where discord_id = " + member.getIdLong());
                HashMap<Long, Integer> exp = new HashMap<>();

                while (results.next()) {

                    int amount = results.getInt("amount");
                    exp.put(member.getIdLong(), amount);

                }

                return exp.get(member.getIdLong());

            } else {

                return -1;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1;

    }

    public void setEXPFromUser(Member member, int amount) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                statement.executeUpdate("update exp set amount = " + amount + " where discord_id = " + member.getIdLong());

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public int getLevelFromUser(Member member) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("select * from exp where discord_id = " + member.getIdLong());
                HashMap<Long, Integer> exp = new HashMap<>();

                while (results.next()) {

                    int level = results.getInt("level");
                    exp.put(member.getIdLong(), level);

                }

                return exp.get(member.getIdLong());

            } else {

                return -1;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1;

    }

    public void setLevelFromUser(@NotNull Member member, int level) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update exp set level = " + level + " where discord_id = " + member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public int getOverallEXPFromUser(Member member) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("select * from exp where discord_id = " + member.getIdLong());
                HashMap<Long, Integer> exp = new HashMap<>();

                while (results.next()) {

                    int amount = results.getInt("overall");
                    exp.put(member.getIdLong(), amount);

                }

                return exp.get(member.getIdLong());

            } else {

                return -1;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1;

    }

    public void setOverallEXPFromUser(@NotNull Member member, int amount) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update exp set overall = " + amount + " where discord_id = " + member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public double getEXPOutput() {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from bot");

            while (results.next()) {

                return results.getDouble("exp_output");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1.0D;

    }

    public void setEXPOutput(double output) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update bot set exp_output = " + output);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getEXPChance() {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from bot");

            while (results.next()) {

                return results.getInt("exp_chance");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public void setEXPChance(int chance) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update bot set exp_chance = " + chance);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /* User Database Management */

    @Deprecated
    public void createUserDBEntry(@NotNull Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into users values(" + member.getIdLong() + ", \"" + member.getUser().getName() + "\", 0, 1)");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Deprecated
    public boolean isInUserDB(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from users");
            List<Long> ids = new ArrayList<>();

            while (results.next()) {

                ids.add(results.getLong("discord_id"));

            }

            return ids.contains(member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public int getMessagesSentFromUser(Member member) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("select * from users where discord_id = " + member.getIdLong());

                while (results.next()) {

                    return results.getInt("messages");

                }

            } else {

                return -1;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1;

    }

    public void setMessagesSentFromUser(Member member, int amount) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                statement.executeUpdate("update users set messages = " + amount + " where discord_id = " + member.getIdLong());

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public boolean isUserOnServer(Member member) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("select on_server from users where id = " + member.getIdLong());
                HashMap<Long, Boolean> onserver = new HashMap<>();

                while (results.next()) {

                    onserver.put(member.getIdLong(), results.getBoolean("on_server"));

                }

                return onserver.get(member.getIdLong());

            } else {

                return false;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public void setUserOnServer(Member member, int state) {

        try {

            if (member != null) {

                connect();
                Statement statement = connection.createStatement();
                statement.executeUpdate("update users set on_server = " + state + " where discord_id = " + member.getIdLong());

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    /* Counter Database Management */

    public int getCounterAmount(String counter) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from counters");

            while (results.next()) {

                return results.getInt(counter);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1;

    }

    public void setCounterAmount(String counter, int amount) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update counters set " + counter + " = " + amount);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    /* Color Database Table Management */

    public boolean isInColorDB(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from colors");
            List<Long> ids = new ArrayList<>();

            while (results.next()) {

                ids.add(results.getLong("discord_id"));

            }

            return ids.contains(member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public void createUserInColorDB(Member member, int color, String role_name, long role_id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into colors values(" + member.getIdLong() + ", " + color + ", \"" + role_name + "\", " + role_id + ")");

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public Color getColorFromUser(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from colors where discord_id = " + member.getIdLong());

            while (results.next()) {

                return new Color(results.getInt("color"));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    public void changeColorFromUser(Member member, Color color) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update colors set color = " + color.getRGB() + " where discord_id = " + member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public String getNameFromColorRoleFromUser(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from colors where discord_id = " + member.getIdLong());

            while (results.next()) {

                return results.getString("role_name");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    public void setNameOfRoleFromUser(Member member, String role_name) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update colors set role_name = \"" + role_name + "\" where discord_id = " + member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public long getColorRoleIdFromUser(Member member) {

        try {

            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from colors where discord_id = " + member.getIdLong());

            while (results.next()) {

                return results.getLong("role_id");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return -1L;

    }

    public void setColorRoleIdFromUser(Member member, long role_id) {

        try {

            connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update colors set role_id = " + role_id + " where discord_id = " + member.getIdLong());

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}
