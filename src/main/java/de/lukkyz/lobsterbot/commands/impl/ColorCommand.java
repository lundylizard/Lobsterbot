package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColorCommand implements Command {

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            if (Lobsterbot.userManager.hasUserRole(Objects.requireNonNull(event.getMember()))) {

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(event.getMember().getColor()).setDescription("Your current color is " + event.getMember().getColor().getRGB() + ".").build()).queue();

            } else {

                //how to get color

            }

        } else if (args.length == 1) {

            // args[0] = color

            if (args[0].equalsIgnoreCase("random")) {


            } else {


            }


        }

    }

}
