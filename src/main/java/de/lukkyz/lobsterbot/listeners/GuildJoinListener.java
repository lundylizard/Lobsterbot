package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class GuildJoinListener extends ListenerAdapter {

    private String joinmessage = "";

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {

        System.out.println("> " + event.getUser().getName() + " joined the server");

        joinmessage += "**WELCOME TO THE LOBSTER GANG**\n\n";
        joinmessage += "We're glad you found your way to our little community.\n";
        joinmessage += "A moderator of ours has to approve you. This might take a while depending on if someone is available right now.\n";
        joinmessage += "Make sure to read the rules and enjoy your stay!\n\n";
        joinmessage += "- The Lobster Gang :lobster:";

        event.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("**WELCOME TO THE LOBSTER GANG**").setDescription(joinmessage).build()).queue());

        Lobsterbot.userManager.createRoleForUser(event.getMember(), event);
        System.out.println("> Created role for " + event.getUser().getName());
        Role userrole = event.getGuild().getRolesByName(event.getUser().getName(), true).get(0);
        event.getGuild().addRoleToMember(event.getMember(), userrole).queue();
        System.out.println("> Gave user role to " + event.getUser().getName());

    }

}
