package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            String content = "";

            try {
                content += "**Lobster Bot** - programmed by " + event.getJDA().getUserById("251430066775392266").getAsMention() + " using JDA\n\n";
            } catch (NullPointerException e) {
                content += "**Lobster Bot** - programmed by **@lundylizard** using JDA\n\n";
            }

            content += "**Server Owner:** " + event.getGuild().getOwner().getAsMention() + "\n";
            content += "Members: " + event.getGuild().getMembers().size() + " | Emotes: " + event.getGuild().getEmotes().size() + "\n\n";
            content += "**Commands executed: **" + Lobsterbot.botManager.getExecutedCommands() + " | **Messages sent:** " + Lobsterbot.botManager.getSentMessages() + "\n";
            content += "Commands (" + CommandHandler.commands.size() + "): \n> " + CommandHandler.commands.keySet().toString().replace("[", "").replace("]", "") + "\n\n";

            event.getMessage().getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("__Server and Bot Info__:").setThumbnail(event.getJDA().getSelfUser().getAvatarUrl()).setColor(Color.ORANGE).build()).queue();


        } else if (args.length >= 1) {

            Member mentioned = event.getMessage().getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
            String content = "";

            content += "**User ID:** " + mentioned.getId() + "\n";
            content += "" + mentioned.getAsMention() + " | " + mentioned.getUser().getName() + (mentioned.getNickname() != null ? " | " + mentioned.getNickname() : "") + "\n";
            content += "**Bot? **" + (mentioned.getUser().isBot() ? "Yes :white_check_mark:" : "No :x:") + "\n";
            content += "Account created on **" + mentioned.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, mentioned.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).length() - 4) + "**\n";
            content += "Server Joined on **" + mentioned.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, mentioned.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).length() - 4) + "**\n";
            content += "**Messages sent:** " + Lobsterbot.userManager.getMessagesSent(mentioned) + "\n";
            content += "**Roles:**\n> ";

            for (int i = 0; i < mentioned.getRoles().size(); i++) {
                Role role = mentioned.getRoles().get(i);
                content += role.getAsMention() + " ";
            }

            event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("__User Info__:").setColor(mentioned.getColor()).setImage(mentioned.getUser().getAvatarUrl()).build()).queue();

        }

    }

}
