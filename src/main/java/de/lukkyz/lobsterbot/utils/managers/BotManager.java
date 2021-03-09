package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.lang.management.ManagementFactory;

public class BotManager {

    private LobsterDatabase database;

    @Contract(pure = true)
    public BotManager(LobsterDatabase lobsterDatabase) {
        this.database = lobsterDatabase;
    }

    @NotNull
    @Contract(pure = true)
    private static String replaceLast(@NotNull final String text, @Nonnull final String regex, @Nonnull final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public String getBotToken() {
        return database.getBotToken();
    }

    public int getExecutedCommands() {
        return database.getExecutedCommandsAmount();
    }

    public void addExecutedCommands(int amount) {
        setExecutedCommands(getExecutedCommands() + amount);
    }

    public void setExecutedCommands(int amount) {
        database.setExecutedCommandsAmount(amount);
    }

    public int getSentMessages() {
        return database.getMessagesSentAmount();
    }

    public void addSentMessages(int amount) {
        setSentMessages(getSentMessages() + amount);
    }

    public void setSentMessages(int amount) {
        database.setMessagesSentAmount(amount);
    }

    public int getCounter(String counter) {
        return database.getCounterAmount(counter);
    }

    public void setCounter(String counter, int amount) {
        database.setCounterAmount(counter, amount);
    }

    public static String getUptime() {

        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;

        String uptime = (years == 0 ? "" : "**" + years + "** years, ") + (months == 0 ? "" : "**" + months + "** months, ") + (days == 0 ? "" : "**" + days + "** days, ") + (hours == 0 ? "" : "**" + hours + "** hours, ")
                + (minutes == 0 ? "" : "**" + minutes + "** minutes, ") + (seconds == 0 ? "" : "**" + seconds + "** seconds, ");

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        return uptime;

    }

    public void addCounter(String counter, int amount) {
        setCounter(counter, getCounter(counter) + amount);
    }

    public String formatToEmoteMessage(String string, @NotNull Member member) {

        for (Emote emote : member.getGuild().getEmotes()) {
            string = string.replace(":" + emote.getName() + ":", "<:" + emote.getName() + ":" + emote.getId() + ">");
        }

        return string;

    }

}
