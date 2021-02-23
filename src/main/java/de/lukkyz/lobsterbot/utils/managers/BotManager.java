package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.Lobsterbot;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;

public class BotManager {

    public String getBotToken() {
        return Lobsterbot.database.getBotToken();
    }

    public int getExecutedCommands() {
        return Lobsterbot.database.getExecutedCommandsAmount();
    }

    public void setExecutedCommands(int amount) {
        Lobsterbot.database.setExecutedCommandsAmount(amount);
    }

    public void addExecutedCommands(int amount) {
        setExecutedCommands(getExecutedCommands() + amount);
    }

    public int getSentMessages() {
        return Lobsterbot.database.getMessagesSentAmount();
    }

    public void setSentMessages(int amount) {
        Lobsterbot.database.setMessagesSentAmount(amount);
    }

    public void addSentMessages(int amount) {
        setSentMessages(getSentMessages() + amount);
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

    @Contract(pure = true)
    @NotNull
    private static String replaceLast(@NotNull final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

}
