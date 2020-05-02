package de.lukkyz.lobsterbot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Lobsterbot {

    /* Bot Variables*/
    public static final String PREFIX = "!";

    /* Lobster Server Channel IDs */

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(Secrets.BOT_TOKEN);
        builder.setAutoReconnect(true);
        builder.setActivity(Activity.streaming("Lobster Gang EP", "https://www.youtube.com/watch?v=RZCF1Sr7yhs"));

        builder.build();

    }

}
