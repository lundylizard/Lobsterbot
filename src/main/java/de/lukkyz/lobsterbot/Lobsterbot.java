package de.lukkyz.lobsterbot;

import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.impl.BdaysCommand;
import de.lukkyz.lobsterbot.commands.impl.InfoCommand;
import de.lukkyz.lobsterbot.commands.impl.ReminderCommand;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import de.lukkyz.lobsterbot.listeners.ReadyListener;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Lobsterbot {

    public static final boolean DEBUG = true;

    public static LobsterDatabase data = new LobsterDatabase();

    /* Bot Variables*/
    public static final String PREFIX = "!";

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(Secrets.BOT_TOKEN);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setAutoReconnect(true);

        /* Register Event Listeners */
        builder.addEventListeners(new MessageListener());
        if (!DEBUG) builder.addEventListeners(new ReadyListener());

        CommandHandler.commands.put("bdays", new BdaysCommand());
        CommandHandler.commands.put("info", new InfoCommand());
        CommandHandler.commands.put("remind", new ReminderCommand());

        builder.build();

        data.connect();

    }

}
