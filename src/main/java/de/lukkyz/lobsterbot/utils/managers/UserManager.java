package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

public class UserManager {

    private LobsterDatabase database;

    @Contract(pure = true)
    public UserManager(LobsterDatabase lobsterDatabase) {
        this.database = lobsterDatabase;
    }

    public void addMessagesSent(Member member, int amount) {
        setMessagesSent(member, getMessagesSent(member) + amount);
    }

    public void setMessagesSent(Member member, int amount) {
        database.setMessagesSentFromUser(member, amount);
    }

    public int getMessagesSent(Member member) {
        return database.getMessagesSentFromUser(member);
    }

    @Deprecated
    public boolean isOnServer(Member member) {
        return database.isUserOnServer(member);
    }

    @Deprecated
    public void setOnServer(Member member, boolean onServer) {
        database.setUserOnServer(member, onServer ? 1 : 0);
    }

    public boolean isModerator(@NotNull Member member) {

        final Role MOD_DEBUG = member.getGuild().getRoleById("797546283606212719"), MODERATOR = member.getGuild().getRoleById("621077872701997062");

        return (Objects.requireNonNull(member.getGuild().getMember(member.getUser())).getRoles().contains(MOD_DEBUG) || Objects.requireNonNull(member.getGuild().getMember(member.getUser())).getRoles().contains(MODERATOR));

    }

    @Deprecated
    public void createUserRole(@Nonnull Member member, Color color) {

        member.getGuild().createRole().setColor(color).setName(member.getUser().getName()).setMentionable(false).queue();

    }

    @Deprecated
    public void giveUserRole(@Nonnull Member member) {

        for (Role role : member.getGuild().getRoles()) {

            if (role.getName().equalsIgnoreCase(member.getUser().getName())) {

                member.getGuild().addRoleToMember(member, role).queue();

            }

        }

    }

    public boolean hasUserRole(@NotNull Member member) {

        Role role = member.getGuild().getRolesByName(member.getUser().getName(), true).get(0);
        return member.getRoles().contains(role);

    }

}
