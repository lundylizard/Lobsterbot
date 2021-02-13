package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class UserManager {

    public void createRoleForUser(@NotNull Member member) {
        member.getGuild().createRole().setName(member.getUser().getName()).setColor(Color.CYAN).queue();
    }

    public void addMessagesSent(Member member, int amount) {
        setMessagesSent(member, getMessagesSent(member) + amount);
    }

    public void setMessagesSent(Member member, int amount) {
        Lobsterbot.database.setMessagesSentFromUser(member, amount);
    }

    public int getMessagesSent(Member member) {
        return Lobsterbot.database.getMessagesSentFromUser(member);
    }

    public boolean isOnServer(Member member) {
        return Lobsterbot.database.isUserOnServer(member);
    }

    public void setOnServer(Member member, boolean onServer) {
        Lobsterbot.database.setUserOnServer(member, onServer ? 1 : 0);
    }

    public boolean isModerator(@NotNull Member member) {

        Role MOD_DEBUG = member.getGuild().getRoleById("797546283606212719");
        Role MODERATOR = member.getGuild().getRoleById("621077872701997062");

        return (member.getGuild().getMember(member.getUser()).getRoles().contains(MOD_DEBUG) || member.getGuild().getMember(member.getUser()).getRoles().contains(MODERATOR));
    }

}
