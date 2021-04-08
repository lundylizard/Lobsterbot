package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Objects;

public class AdminCommand implements Command {

    @Override
    public void action(@Nonnull String[] args, @Nonnull MessageReceivedEvent event) {

        if (event.getAuthor().getIdLong() == 251430066775392266L) {
            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("create_roles")) {
                    for (Member member : event.getGuild().getMembers()) {
                        if (!member.getUser().isBot()) {

                            event.getGuild().createRole().setName(Objects.requireNonNull(member).getUser().getName()).setMentionable(false).queue();

                        }

                    }

                }

            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("multiplier")) {
                    if (isDouble(args[1])) {

                        Lobsterbot.database.setEXPOutput(Double.parseDouble(args[1]));
                        event.getTextChannel().sendMessage("Changed EXP Multiplier to " + args[1]).queue();

                    } else {

                        event.getTextChannel().sendMessage("Argument not valid.").queue();

                    }

                }

                if (args[0].equalsIgnoreCase("exp_chance")) {
                    if (isInt(args[1])) {

                        Lobsterbot.database.setEXPChance(Integer.parseInt(args[1]));
                        event.getTextChannel().sendMessage("Changed Additional EXP Chance to " + ((double) (Lobsterbot.experienceManager.getAdditionalEXPChance() / 10)) + "%").queue();

                    } else {

                        event.getTextChannel().sendMessage("Argument not valid.").queue();

                    }

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

    private boolean isInt(String input) {

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

}
