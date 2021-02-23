package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements Command {

    public static List<Long> awaitingMySQLInput = new ArrayList<>();

    @Override
    public void action(@Nonnull String[] args, @Nonnull MessageReceivedEvent event) {

        if (event.getAuthor().getIdLong() == 251430066775392266L) {

            if (args.length == 2) {

                if (args[0].equalsIgnoreCase("multiplier")) {
                    if (isDouble(args[1])) {

                        Lobsterbot.database.setEXPOutput(Double.parseDouble(args[1]));
                        event.getTextChannel().sendMessage("Changed EXP Multiplier to " + args[1]).queue();

                    } else {

                        event.getTextChannel().sendMessage("Argument not valid.").queue();

                    }

                }

            } else if (args.length == 1) {

                if (args[0].equalsIgnoreCase("execute")) {

                    event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("Please enter MySQL query you want to execute:").setFooter("To cancel, type 'cancel'").build()).queue();
                    awaitingMySQLInput.add(event.getAuthor().getIdLong());

                }

            }

        } else {

            event.getTextChannel().sendMessage("Only lundylizard can execute this command.").queue();

        }

    }

    private boolean isDouble(String input) {

        try {

            Double.parseDouble(input);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static class AdminCommandEvents extends ListenerAdapter {

        @Override
        public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

            if (awaitingMySQLInput.contains(event.getAuthor().getIdLong())) {

                if (event.getMessage().getContentRaw().equalsIgnoreCase("cancel")) {

                    awaitingMySQLInput.remove(event.getAuthor().getIdLong());
                    event.getTextChannel().sendMessage("Canceled MySQL command execution process.").queue();

                }

                if (!event.getMessage().getContentRaw().startsWith("!")) {

                    event.getTextChannel().sendMessage("Executing `" + event.getMessage().getContentRaw() + "`...").queue();
                    awaitingMySQLInput.remove(event.getAuthor().getIdLong());

                    if (Lobsterbot.database.executeQuery(event.getMessage().getContentRaw())) {
                        event.getTextChannel().sendMessage("").queue();
                    } else {

                    }

                }

            }

        }

    }

}
