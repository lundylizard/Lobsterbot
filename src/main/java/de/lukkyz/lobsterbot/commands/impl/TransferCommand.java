package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TransferCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (event.getAuthor().getIdLong() == 251430066775392266L) {

            event.getTextChannel().sendMessage("Starting transfer of data...").queue();
            event.getTextChannel().sendMessage("Failed.").queue();

        } else {

            event.getTextChannel().sendMessage("Only lundylizard can execute this command.").queue();

        }

    }

}
