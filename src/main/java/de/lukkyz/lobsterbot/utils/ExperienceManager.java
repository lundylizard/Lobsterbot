package de.lukkyz.lobsterbot.utils;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.entities.Member;

public class ExperienceManager {

    private LobsterDatabase database = Lobsterbot.database;

    public int getEXPfromDB(Member member) {
        return database.getEXPfromUser(member);
    }

    public void addEXP(Member member, int amount) {

        database.addEXPToUser(member, database.getEXPfromUser(member) + amount);

    }

    public int getLevel(Member member) {
        return 0;
    }

    public void setLevel(Member member) {

    }

    public int calculateEXPneeded(int level) {
        return 500 * ((int) Math.pow(level, 2)) - (500 * level) + 100;
    }

    public int getEXP(Member member) {
        return 0;
    }

    public void inIsEXPDB(Member member) {

    }

}
