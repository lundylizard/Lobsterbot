package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.entities.Member;

public class BlacklistManager {

    public boolean userBlacklisted(Member member) {
        return Lobsterbot.database.getUserBlacklisted(member.getIdLong());
    }

    public void addUserToBlacklist(long id, String name) {
        Lobsterbot.database.setUserBlacklisted(id, true, name);
    }

    public void removeUserFromBlacklist(long id) {
        Lobsterbot.database.setUserBlacklisted(id, false, "");
    }

}
