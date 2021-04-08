package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class LeaderboardCommand implements Command {

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            List<String> leaderboard = Lobsterbot.experienceManager.getEXPLeaderboard(event, 10);
            StringBuilder output = new StringBuilder();

            for (String s : leaderboard) {
                output.append(s).append("\n");
            }

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Lobster Gang EXP Leaderboard").setColor(Color.ORANGE).setDescription(output.toString()).build()).queue();

        }

    }

}
