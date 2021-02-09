package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReminderCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        int time;
        String reason = "";
        TimeUnit timeunit;

        if (args.length < 3) {

            event.getTextChannel().sendMessage("Missing arguments!\nPlease use: `!remind <time> <unit> <reason>`").queue();

        } else if (args.length > 3) {

            try {

                timeunit = TimeUnit.valueOf(args[1].toUpperCase());

                try {
                    time = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    event.getTextChannel().sendMessage("`" + args[0] + "` is not a number... How am I supposed to remind you in `" + args[0] + "` " + timeunit.name().toLowerCase() + "?!").queue();
                    return;
                }

                for (int i = 2; i < args.length; i++) {
                    //reason += args[i] + " ";
                    reason = new StringBuilder().append(args[i] + " ").toString();
                }

                String reason_f = reason;
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setTitle("Reminder set!").setDescription("Reminding you in **" + time + " " + timeunit.name().toLowerCase() + "** about " + reason.substring(0, reason.length() - 1) + "!").build()).queue();
                event.getAuthor().openPrivateChannel().queueAfter(time, timeunit, (channel -> channel.sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription("Hey! I'm here to remind you about **" + reason_f.substring(0, reason_f.length() - 1) + "!**").build()).queue()));

            } catch (IllegalArgumentException e) {

                List<Enum> enumValues = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription(args[1] + " is not a valid unit. Please use " + enumValues.toString() + "").build()).queue();

            }

        }

    }

}
