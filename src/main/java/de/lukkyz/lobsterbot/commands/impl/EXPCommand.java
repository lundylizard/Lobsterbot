package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class EXPCommand implements Command {

    private String output = "";

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {
            if (Lobsterbot.database.isInEXPDB(event.getMember())) {

                output += ":sparkles: EXP: " + Lobsterbot.experienceManager.getEXP(event.getMember()) + " | :lobster: Level: " + Lobsterbot.experienceManager.getLevel(event.getMember()) + "\n\n";
                output += "Experience needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + "\n";
                output += "Overall EXP: " + Lobsterbot.experienceManager.getOverallEXP(event.getMember());

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription(output).build()).queue();

                output = "";

            } else {

                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you currently have no experience. Start typing and gain experience! :sparkles:").queue();

            }

        } else {

            Member target = event.getMessage().getMentionedMembers().get(0);

            output += ":sparkles: EXP: " + Lobsterbot.experienceManager.getEXP(target) + " | :lobster: Level: " + Lobsterbot.experienceManager.getLevel(target) + "\n\n";
            output += "Experience needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + "\n";
            output += "Overall EXP: " + Lobsterbot.experienceManager.getOverallEXP(event.getMember());

            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(0x032261).setTitle(target.getEffectiveName() + "s EXP: ").setDescription(output).build()).queue();

            output = "";

        }

    }


}
