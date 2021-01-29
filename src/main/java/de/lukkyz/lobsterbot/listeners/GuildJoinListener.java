package de.lukkyz.lobsterbot.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class GuildJoinListener extends ListenerAdapter {

    private String joinmessage = "";

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {

        joinmessage += "**WELCOME TO THE LOBSTER GANG**\n\n";
        joinmessage += "We're glad you found your way to our little community.\n";
        joinmessage += "A moderator of ours has to approve you. This might take a while depending on if someone is available right now.\n";
        joinmessage += "Meanwhile you can look at the beautiful colors you can choose (You can change it whenever).\n";
        joinmessage += "Make sure to read the rules and enjoy your stay!\n\n";
        joinmessage += "- The Lobster Gang :lobster:";

        event.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(joinmessage).queue());

    }

}
