package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.CommandParser;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class InfoCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String content = "";

        try {
            content += "**Lobster Bot** - programmed by " + event.getJDA().getUserById("251430066775392266").getAsMention() + " using JDA\n\n";
        } catch (NullPointerException e) {
            content += "**Lobster Bot** - programmed by **@lundylizard** using JDA\n\n";
        }

        content += "**Server Owner:** " + event.getGuild().getOwner().getAsMention() + "\n";
        content += "Members: " + event.getGuild().getMembers().size() + " | Emotes: " + event.getGuild().getEmotes().size() + "\n\n";

        content += "Commands (" + CommandHandler.commands.size() + "): \n> " + CommandHandler.commands.keySet().toString().replace("[", "").replace("]", "") + "\n";
        content += "__Commands executed this session:__ **" + CommandParser.count + "** | __Messages sent:__ **" + MessageListener.count + "**";

        event.getMessage().getTextChannel().sendMessage(new EmbedBuilder().setDescription(content).setTitle("Server and Bot Info").setFooter(event.getMessage().getId() + "-" + event.getTextChannel().getName(), event.getAuthor().getAvatarUrl()).setThumbnail(event.getJDA().getSelfUser().getAvatarUrl()).setColor(Color.RED).build()).queue();



    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }
}
