package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class BdaysCommand implements Command {

    private LobsterDatabase database = new LobsterDatabase();

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Lobster Gang Birthdays**").setColor(Color.RED).setDescription(database.getBdays()).build()).queue();

        } else if (args.length <= 4 && args.length > 0) {

            if (args[0].equalsIgnoreCase("add")) {

                event.getTextChannel().sendMessage("Added user to birthday database.").queue();
                database.insertBday(0, Integer.parseInt(args[3]), Integer.parseInt(args[2]), args[1]);

            } else if (args[0].equalsIgnoreCase("remove")) {

                event.getTextChannel().sendMessage("Removed user from birthday database.").queue();
                database.deleteBday(args[1]);

            }

        } else if (args.length < 4) {

            event.getTextChannel().sendMessage("Insufficient arguments. Usage: !bdays [add/remove] [name] (day month)").queue();

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
