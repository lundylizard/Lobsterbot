package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.Random;

public class BdaysCommand implements Command {

    //TODO REMAKE

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length < 1) {

            Lobsterbot.data.connect();
            String bdays = Lobsterbot.data.getBdays();

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Official Lobster Gang Birthdays**").setColor(new Random().nextInt()).setDescription(bdays.replace("#", "\n")).build()).queue();


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

    private String decodeBirthday(String input) throws IOException {
        /* FORMAT: "dd/mm-name#dd/mm-name_with_spaces#" */


        if (input != null) {

            String[] bday = input.split("#");

            String formatted_string = "";

            for (int i = 0; i < bday.length; i++) {

                String days = bday[i].substring(0, 2);
                String month = bday[i].substring(3, 5);
                String name = bday[i].split("-")[1];
                formatted_string += days + "/" + month + " -- " + name + "\n";

            }

            return formatted_string.replace("_", " ");


        } else {

            return "File Not Found " + Utils.generateEmote();

        }

    }

}
