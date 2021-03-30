package de.lukkyz.lobsterbot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

public class GuildJoinListener extends ListenerAdapter {

    private String joinmessage = "";

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {

        System.out.println("> " + event.getUser().getName() + " joined the server!");

        joinmessage += "We're glad you found your way to our little community.\n";
        joinmessage += "A moderator of ours has to approve you. This might take a while depending on if someone is available right now.\n";
        joinmessage += "Make sure to read the rules and enjoy your stay!\n\n";
        joinmessage += "- The Lobster Gang :lobster:";

        event.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("**WELCOME TO THE LOBSTER GANG**").setDescription(joinmessage).build()).queue());
        System.out.println("> " + event.getUser().getName() + " joined the server");
        event.getGuild().createRole().setName(Objects.requireNonNull(event.getMember()).getUser().getName()).setMentionable(false).queue();

    }
}
