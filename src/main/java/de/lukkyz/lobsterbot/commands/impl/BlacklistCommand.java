package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class BlacklistCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (event.getAuthor().getIdLong() == 251430066775392266L) {
            if (args.length == 0) {

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.CYAN).setTitle("Blacklisted users").setDescription(Lobsterbot.database.getBlacklistedUsers(event)).build()).queue();

            } else if (args.length >= 1) {

                Member member = event.getMessage().getMentionedMembers().get(0);
                // args[0] = [add/remove]; args[1] = discord id; args[2] name

                if (args[0].equalsIgnoreCase("add")) {

                    try {

                        long id = Long.parseLong(args[1]);


                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        event.getTextChannel().sendMessage(args[1] + " is not a valid ID.").queue();
                    }

                } else if (args[0].equalsIgnoreCase("remove")) {

                } else {

                }

            }

        } else {

            event.getTextChannel().sendMessage("Only lundylizard can execute this command.").queue();

        }

    }
}
