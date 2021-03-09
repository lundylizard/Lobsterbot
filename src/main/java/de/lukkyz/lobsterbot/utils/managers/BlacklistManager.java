package de.lukkyz.lobsterbot.utils.managers;

import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BlacklistManager {

    private LobsterDatabase database;

    @Contract(pure = true)
    public BlacklistManager(LobsterDatabase lobsterDatabase) {
        this.database = lobsterDatabase;
    }

    public boolean userBlacklisted(@NotNull Member member) {
        return database.getUserBlacklisted(member.getIdLong());
    }

    @Deprecated
    public void addUserToBlacklist(long id, String name) {
        database.setUserBlacklisted(id, true, name);
    }

    @Deprecated
    public void removeUserFromBlacklist(long id) {
        database.setUserBlacklisted(id, false, "");
    }

}
