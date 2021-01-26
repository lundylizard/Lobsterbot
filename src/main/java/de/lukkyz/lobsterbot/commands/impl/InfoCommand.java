package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length == 0) {

            String content = "";

            try {
                content += "**Lobster Bot** - programmed by " + event.getJDA().getUserById("251430066775392266").getAsMention() + " using JDA\n\n";
            } catch (NullPointerException e) {
                content += "**Lobster Bot** - programmed by **@lundylizard** using JDA\n\n";
            }

            content += "**Server Owner:** " + event.getGuild().getOwner().getAsMention() + "\n";
            content += "Members: " + event.getGuild().getMembers().size() + " | Emotes: " + event.getGuild().getEmotes().size() + "\n\n";
            content += "Commands (" + CommandHandler.commands.size() + "): \n> " + CommandHandler.commands.keySet().toString().replace("[", "").replace("]", "") + "\n\n";

            event.getMessage().getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("__Server and Bot Info__:").setFooter(event.getMessage().getId() + "-" + event.getTextChannel().getName(), event.getAuthor().getAvatarUrl()).setThumbnail(event.getJDA().getSelfUser().getAvatarUrl()).setColor(Color.RED).build()).queue();


        } else if (args.length >= 1) {

            Member mentioned = event.getMessage().getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
            String content = "";

            content += "**User ID:** " + mentioned.getId() + "\n";
            content += "" + mentioned.getAsMention() + " | " + mentioned.getUser().getName() + (mentioned.getNickname() != null ? " | " + mentioned.getNickname() : "") + "\n";
            content += "**Bot? **" + (mentioned.getUser().isBot() ? "Yes :white_check_mark:" : "No :x:") + "\n";
            content += "Account created on **" + mentioned.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, mentioned.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).length() - 4) + "**\n";
            content += "Server Joined on **" + mentioned.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ").substring(0, mentioned.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).length() - 4) + "**\n";
            content += "**Roles:**\n> ";

            for (int i = 0; i < mentioned.getRoles().size(); i++) {
                Role role = mentioned.getRoles().get(i);
                content += role.getAsMention() + " ";
            }

            event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("__User Info__:").setFooter(event.getMessage().getId() + "-" + event.getTextChannel().getName(), event.getAuthor().getAvatarUrl()).setThumbnail(mentioned.getUser().getAvatarUrl()).setColor(mentioned.getColor()).build()).queue();

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
