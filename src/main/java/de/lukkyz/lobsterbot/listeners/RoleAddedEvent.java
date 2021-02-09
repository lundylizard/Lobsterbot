package de.lukkyz.lobsterbot.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class RoleAddedEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {

        if (event.getRoles().get(0).getName().equalsIgnoreCase("Proletariat ")) {

            System.out.println("> " + event.getUser().getName() + " got verified.");

        }

    }

}
