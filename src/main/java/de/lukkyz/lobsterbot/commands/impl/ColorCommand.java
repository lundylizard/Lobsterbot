package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class ColorCommand implements Command {

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        Role role;

        try {

            role = event.getMember().getGuild().getRolesByName(event.getMember().getUser().getName(), true).get(0);

        } catch (IndexOutOfBoundsException e) {

            event.getGuild().createRole().setColor(Color.CYAN).setName(Objects.requireNonNull(event.getMember()).getUser().getName()).setMentionable(false).queue();
            role = event.getMember().getGuild().getRolesByName(event.getMember().getUser().getName(), true).get(0);

        }

        if (args.length == 0) {

            if (event.getMember().getColor() != null) {

                String hex = Integer.toHexString(event.getMember().getColor().getRGB());
                hex = "#" + hex.substring(2);

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(event.getMember().getColor()).setDescription("Your current color is " + hex + ".\nTo manage your color use `!color [hex]`. (*Example: !color #032261*)").build()).queue();

            } else {

                event.getTextChannel().sendMessage("no color").queue();

            }

        } else if (args.length == 1) {

            Color color;

            try {

                if (args[0].startsWith("#")) {

                    color = Color.decode(args[0]);

                } else {

                    color = Color.decode("#" + args[0]);

                }

                event.getGuild().getRolesByName(event.getAuthor().getName(), true).get(0).getManager().setColor(color).queue();
                event.getGuild().addRoleToMember(event.getMember(), role).queue();
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(color).setTitle("Changed user color!").setDescription("Successfully set your color.\nTo manage your color use `!color [#hex]`. (*Example: !color #032261*)").build()).queue();

            } catch (NumberFormatException e) {

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occured").setDescription("The color you provided is not valid.\nPlease enter a valid hex-code.").build()).queue();

            } catch (IndexOutOfBoundsException e) {

                event.getGuild().createRole().setColor(Color.CYAN).setName(Objects.requireNonNull(event.getMember()).getUser().getName()).setMentionable(false).queue();

            }

        }

    }

}
