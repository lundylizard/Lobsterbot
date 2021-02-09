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

    private LobsterDatabase database = Lobsterbot.database;

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

    public int calculateEXPneeded(int level) {
        return 500 * ((int) Math.pow(level, 2)) - (500 * level) + 100;
    }

    public List<String> getEXPLeaderboard(MessageReceivedEvent event) {

        List<String> leaderboard = new ArrayList<>();
        int rank = 0;

        try {

            LobsterDatabase.connect();
            Statement statement = LobsterDatabase.connection.createStatement();
            ResultSet results = statement.executeQuery("select * from exp order by exp.level DESC, exp.amount DESC");

            while (results.next()) {

                Member member = event.getGuild().getMemberById(results.getLong("discord_id"));

                rank++;
                if (getOverallEXP(member) != -101 && member != null)
                    leaderboard.add(rank + ") " + member.getAsMention() + " -- [:lobster: Level " + results.getInt("level") + "] Overall EXP: " + getOverallEXP(event.getGuild().getMemberById(results.getLong("discord_id"))));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return leaderboard;

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


}