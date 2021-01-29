package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EXPCommand implements Command {

    String output = "";

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (Lobsterbot.database.isInEXPDB(event.getMember())) {

            output += ":sparkles: EXP: " + Lobsterbot.database.getEXPfromUser(event.getMember()) + " | :lobster: Level: " + Lobsterbot.database.getLevelFromUser(event.getMember()) + "\n\n";
            output += "Experience needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.database.getLevelFromUser(event.getMember())) - Lobsterbot.database.getEXPfromUser(event.getMember()));
            //todo progression bar

            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(0x032261).setDescription(output).build()).queue();

            output = "";

        } else {

            event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you currently have no experience. Start typing and gain experience! :sparkles:").queue();

        }

    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
