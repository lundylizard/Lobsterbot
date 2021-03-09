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

            List<String> leaderboard = Lobsterbot.experienceManager.getEXPLeaderboard(event);
            StringBuilder output = new StringBuilder();

            if (Lobsterbot.experienceManager.getEXPMultiplier() != 1.0D) {
                output.append("*Current EXP Multiplier: ").append(Lobsterbot.experienceManager.getEXPMultiplier()).append("*\n\n");
            }

            for (String s : leaderboard) {
                output.append(s).append("\n");
            }

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Lobster Gang EXP Leaderboard**").setColor(Color.RED).setDescription(output.toString()).build()).queue();

        }

    }

}
