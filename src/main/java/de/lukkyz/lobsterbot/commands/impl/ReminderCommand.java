package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReminderCommand implements Command {

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        int time;
        StringBuilder reason = new StringBuilder();
        TimeUnit timeunit;

        if (args.length < 3) {

            event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("Missing arguments!\nPlease use: `!remind <time> <unit> <reason>`").setColor(Color.RED).build()).queue();

        } else {

            try {

                timeunit = TimeUnit.valueOf(args[1].toUpperCase());

                try {

                    time = Integer.parseInt(args[0]);

                } catch (NumberFormatException e) {

                    event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("An Error occurred").setDescription("`" + args[0] + "` is not a valid number!").setColor(Color.RED).build()).queue();
                    return;

                }

                for (int i = 2; i < args.length; i++) {

                    reason.append(args[i]).append(" ");

                }

                String reason_f = reason.toString();
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setTitle("Reminder set!").setDescription("Reminding you in **" + time + " " + timeunit.name().toLowerCase() + "** about *" + reason + "*!").build()).queue();
                event.getAuthor().openPrivateChannel().queueAfter(time, timeunit, (channel -> channel.sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription("Hey! I'm here to remind you about **" + reason_f + "**!").setTitle("Reminder!").build()).queue()));

            } catch (IllegalArgumentException e) {

                List<Enum> enumValues = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription(args[1] + " is not a valid unit. Please use `" + enumValues.toString().toLowerCase() + "`").build()).queue();

            }

        }

    }

}
