package de.lukkyz.lobsterbot.utils.managers;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class UserManager {

    public void createRoleForUser(Member member, GuildMemberJoinEvent event) {
        event.getGuild().createRole().setName(member.getUser().getName()).queue();
    }

}
