package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class BdaysCommand implements Command {

    //TODO REMAKE

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length < 1) {

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Official Lobster Gang Birthdays**").setColor(new Random().nextInt()).setDescription(decodeBirthday(/* TODO */null)).build()).queue();

        } else {

            if (args[1] == "add") {


            }

        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    private String decodeBirthday(String input) {
        /* "09/09-lucas#10/09-the_lobster_gang#" */

        if (input != null) {

            String[] date = input.split("/");


        } else {

            return "File Not Found";

        }


        return "null";

    }

}
