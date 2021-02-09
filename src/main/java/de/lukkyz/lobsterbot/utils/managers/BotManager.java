package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.Lobsterbot;

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
        Lobsterbot.database.setExecutedCommandsAmount(getExecutedCommands() + amount);
    }

    public int getSentMessages() {
        return Lobsterbot.database.getMessagesSentAmount();
    }

    public void setSentMessages(int amount) {
        Lobsterbot.database.setMessagesSentAmount(amount);
    }

    public void addSentMessages(int amount) {
        Lobsterbot.database.setMessagesSentAmount(getSentMessages() + amount);
    }

}
