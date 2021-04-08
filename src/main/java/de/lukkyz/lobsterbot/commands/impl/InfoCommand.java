package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.utils.managers.BotManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class InfoCommand implements Command {

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            String content = "";
            final User lundy = event.getJDA().getUserById("251430066775392266");
            final Member owner = event.getGuild().getOwner();

            content += "**Lobster Bot** [v" + Lobsterbot.VERSION + "." + Lobsterbot.BUILD + "] - programmed by " + Objects.requireNonNull(lundy).getAsMention() + " using JDA\n";
            content += "*Lines of code: 2450*\n\n";
            content += "Bot online since " + BotManager.getUptime() + "\n";
            content += owner != null ? "**Server Owner:** " + owner.getAsMention() + "\n" : "";
            content += "**Members:** " + event.getGuild().getMembers().size() + " | **Emotes:** " + event.getGuild().getEmotes().size() + "\n\n";
            content += "[Records] Commands executed: **" + Lobsterbot.botManager.getExecutedCommands() + "** | Messages sent: **" + Lobsterbot.botManager.getSentMessages() + "**\n\n";
            content += "Commands (" + CommandHandler.commands.size() + "): \n> " + CommandHandler.commands.keySet().toString().replace("[", "").replace("]", "") + "\n\n";

            event.getMessage().getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("__Server and Bot Info__:").setThumbnail(event.getJDA().getSelfUser().getAvatarUrl()).setColor(Color.ORANGE).build()).queue();

        } else if (args.length == 1) {

            if (!args[0].equalsIgnoreCase("uptime")) {

                Member mentioned = event.getMessage().getGuild().getMember(event.getMessage().getMentionedUsers().get(0));

                if (mentioned != null) {

                    String content = "";

                    content += "**User ID:** " + mentioned.getId() + "\n";
                    content += mentioned.getAsMention() + " | " + mentioned.getEffectiveName() + "\n\n";
                    content += "Account created on **" + mentioned.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, mentioned.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).length() - 4) + "**\n";
                    content += "Server Joined on **" + mentioned.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, mentioned.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).length() - 4) + "**\n\n";
                    content += "**Messages recorded:** " + Lobsterbot.userManager.getMessagesSent(mentioned) + "\n\n";
                    content += "**Roles (" + mentioned.getRoles().size() + "):**\n> ";

                    for (int i = 0; i < mentioned.getRoles().size(); i++) {

                        Role role = mentioned.getRoles().get(i);
                        content += role.getAsMention() + " ";

                    }

                    event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("__User Info__:").setColor(mentioned.getColor()).setThumbnail(mentioned.getUser().getAvatarUrl()).build()).queue();

                } else {

                    event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("Could not find user mentioned.").setColor(Color.RED).build()).queue();

                }

            } else {

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setTitle("Bot Uptime").setDescription("Lobster Bot is up since " + BotManager.getUptime() + ".").build()).queue();

            }

        }

    }

}
