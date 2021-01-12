package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ReminderCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        int time = 0;
        String reason = "";

        if (args.length < 2) {
            event.getTextChannel().sendMessage(Utils.generateHelpString("`!remind <time in mins> <reason>`")).queue();
        } else if (args.length >= 2) {

            try {
                time = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                event.getTextChannel().sendMessage("`" + args[0] + "` is not a number... How am I supposed to remind you in `" + args[0] + "` minutes?!").queue();
                return;
            }

            for (int i = 1; i < args.length; i++) {
                reason += args[i] + " ";
            }

            String reason_f = reason;
            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription("Reminding you in **" + time + "** " + (time > 1 || time <= 0 ? "minutes" : "minute") + " about `" + reason.substring(0, reason.length() - 1) + "`!").build()).queue();
            event.getAuthor().openPrivateChannel().queueAfter(time, TimeUnit.MINUTES, (channel -> channel.sendMessage("Hey! I'm here to remind you about `" + reason_f.substring(0, reason_f.length() - 1).toUpperCase() + "`! " + Utils.generateEmote() + Utils.generateEmote()).queue()));

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
