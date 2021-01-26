package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class LeaderboardCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            List<String> leaderboard = Lobsterbot.database.getLeaderboard(event);
            String output = "";

            for (int i = 0; i < leaderboard.size(); i++) {

                output += leaderboard.get(i) + "\n";

            }

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Lobster Gang Leaderboard**").setColor(Color.RED).setDescription(output).build()).queue();

        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }
}
