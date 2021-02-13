package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class RoleAddedEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {

        if (event.getRoles().get(0).getName().equalsIgnoreCase("Proletariat")) {

            System.out.println("> " + event.getUser().getName() + " got verified");

            Lobsterbot.userManager.createRoleForUser(event.getMember());
            System.out.println("> Created role for " + event.getUser().getName());
            Role userrole = event.getGuild().getRolesByName(event.getUser().getName(), true).get(0);
            event.getGuild().addRoleToMember(event.getMember(), userrole).queue();
            System.out.println("> Gave user role to " + event.getUser().getName());

            if (!Lobsterbot.database.isInUserDB(event.getMember())) {

                Lobsterbot.database.createUserDBEntry(event.getMember());
                System.out.println("> Created User Database Entry for " + event.getUser().getName());

            }

        }

    }

}
