package de.lukkyz.lobsterbot;

import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.impl.BdaysCommand;
import de.lukkyz.lobsterbot.commands.impl.InfoCommand;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Lobsterbot {

    /* Bot Variables*/
    public static final String PREFIX = "!";

    /* Lobster Server Channel IDs */

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(Secrets.BOT_TOKEN);
        builder.setAutoReconnect(true);

        /* Register Event Listeners */
        builder.addEventListeners(new MessageListener());

        CommandHandler.commands.put("bdays", new BdaysCommand());
        CommandHandler.commands.put("info", new InfoCommand());

        builder.build();

    }

}
