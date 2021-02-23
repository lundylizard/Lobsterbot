package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExperienceManager {

    private final LobsterDatabase database = Lobsterbot.database;

    public void addEXP(Member member, int amount) {
        database.setEXPFromUser(member, (getEXP(member) + amount));
    }

    public void setEXP(Member member, int amount) {
        database.setEXPFromUser(member, amount);
    }

    public int getEXP(Member member) {
        return database.getEXPfromUser(member);
    }

    public void addLevel(Member member, int level) {
        setLevel(member, (getLevel(member) + level));
    }

    public void setLevel(Member member, int level) {
        database.setLevelFromUser(member, level);
    }

    public int getLevel(Member member) {
        return database.getLevelFromUser(member);
    }

    public void addOverallEXP(Member member, int amount) {
        setOverallEXP(member, (getOverallEXP(member) + amount));
    }

    public void setOverallEXP(Member member, int amount) {
        database.setOverallEXPFromUser(member, amount);
    }

    public int getOverallEXP(Member member) {
        return database.getOverallEXPFromUser(member);
    }

    public List<String> getEXPLeaderboard(MessageReceivedEvent event) {

        List<String> leaderboard = new ArrayList<>();
        int rank = 0;
        String[] ranking = {":first_place:", ":second_place:", ":third_place:", "4", "5", "6", "7", "8", "9", "10"};

        try {

            LobsterDatabase.connect();
            Statement statement = LobsterDatabase.connection.createStatement();
            ResultSet results = statement.executeQuery("select * from exp order by exp.level desc, exp.amount desc");

            while (results.next() && rank < 10) {

                Member member = event.getGuild().getMemberById(results.getLong("discord_id"));

                if (member != null) {

                    rank++;
                    leaderboard.add((ranking[rank - 1].startsWith(":") ? ranking[rank - 1] + ") " : " **" + rank + "**) ") + member.getAsMention() + " -- [Level " + results.getInt("level") + "] Overall EXP: **" + getOverallEXP(event.getGuild().getMemberById(results.getLong("discord_id"))) + "**");

                }

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return leaderboard;

    }

    public int calculateEXPneeded(int level) {
        return 500 * ((int) Math.pow(level, 2)) - (500 * level) + 100;
    }

    public double getEXPMultiplier() {
        return database.getEXPOutput();
    }

    public double getPercentage(Member member) {
        return Math.round(((double) getEXP(member) / calculateEXPneeded(getLevel(member))) * 100d);
    }

    // credits to lu
    public String getProgressionBar(Member member) {

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < ((int) getPercentage(member) / 10); i++) {
            buffer.append("#");
        }
        while (10 > buffer.length()) {
            buffer.append("0");
        }

        return buffer.toString().replace("#", ":yellow_square:").replace("0", ":black_large_square:");

    }

}
