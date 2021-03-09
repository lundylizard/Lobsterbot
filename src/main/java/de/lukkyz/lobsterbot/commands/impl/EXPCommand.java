package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EXPCommand implements Command {

    private String output = "";

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {
            if (Lobsterbot.database.isInEXPDB(event.getMember())) {

                if (Lobsterbot.experienceManager.getEXPMultiplier() != 1.0D) {
                    output += "*Current EXP Multiplier: " + Lobsterbot.experienceManager.getEXPMultiplier() + "*\n\n";
                }

                output += ":sparkles: **EXP:** " + Lobsterbot.experienceManager.getEXP(event.getMember()) + " | :lobster: **Level:** " + Lobsterbot.experienceManager.getLevel(event.getMember()) + "\n\n";
                output += "**Experience needed for next level:** " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + "\n";
                output += "**Overall EXP:** " + Lobsterbot.experienceManager.getOverallEXP(event.getMember()) + "\n\n";
                output += Lobsterbot.experienceManager.getProgressionBar(event.getMember()) + " » **" + Lobsterbot.experienceManager.getPercentage(event.getMember()) + "%**";

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription(output).build()).queue();

                output = "";

            } else {

                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you currently have no experience. Start typing and gain experience! :sparkles:").queue();

            }

        } else {

            final Member target = event.getMessage().getMentionedMembers().get(0);

            if (target != null) {

                if (Lobsterbot.experienceManager.getEXPMultiplier() != 1.0D) {
                    output += "*Current EXP Multiplier: " + Lobsterbot.experienceManager.getEXPMultiplier() + "*\n\n";
                }

                output += ":sparkles: **EXP:** " + Lobsterbot.experienceManager.getEXP(target) + " | :lobster: **Level:** " + Lobsterbot.experienceManager.getLevel(target) + "\n\n";
                output += "**Experience needed for next level: **" + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(target)) - Lobsterbot.experienceManager.getEXP(target)) + "\n";
                output += "**Overall EXP: **" + Lobsterbot.experienceManager.getOverallEXP(target) + "\n\n";
                output += Lobsterbot.experienceManager.getProgressionBar(target) + " » **" + Lobsterbot.experienceManager.getPercentage(target) + "%**";

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setTitle(target.getEffectiveName().endsWith("s") ? target.getEffectiveName() + "' EXP" : target.getEffectiveName() + "'s EXP").setDescription(output).build()).queue();

                output = "";

            } else {


            }

        }

    }

}
