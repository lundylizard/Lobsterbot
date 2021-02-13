package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

public class BlacklistManager {

    public boolean userBlacklisted(@NotNull Member member) {
        return Lobsterbot.database.getUserBlacklisted(member.getIdLong());
    }

    @Deprecated
    public void addUserToBlacklist(long id, String name) {
        Lobsterbot.database.setUserBlacklisted(id, true, name);
    }

    @Deprecated
    public void removeUserFromBlacklist(long id) {
        Lobsterbot.database.setUserBlacklisted(id, false, "");
    }

}
