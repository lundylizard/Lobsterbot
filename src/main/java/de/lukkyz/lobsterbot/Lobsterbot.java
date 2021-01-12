package de.lukkyz.lobsterbot;

import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.impl.BdaysCommand;
import de.lukkyz.lobsterbot.commands.impl.InfoCommand;
import de.lukkyz.lobsterbot.commands.impl.LeaderboardCommand;
import de.lukkyz.lobsterbot.commands.impl.ReminderCommand;
import de.lukkyz.lobsterbot.listeners.GuildJoinListener;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import de.lukkyz.lobsterbot.listeners.MessageReactListener;
import de.lukkyz.lobsterbot.listeners.ReadyListener;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Lobsterbot {

    public static final boolean DEBUG = false;

    public static LobsterDatabase data = new LobsterDatabase();
    public static JDABuilder builder;

    /* Bot Variables*/
    public static final String PREFIX = "!";

    public static void main(String[] args) throws LoginException {

        builder = new JDABuilder(AccountType.BOT);

        builder.setToken(data.getBotToken());
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setAutoReconnect(true);
        builder.setActivity(DEBUG ? Activity.playing("in debug mode.") : Activity.listening("commands."));

        /* Register Event Listeners */
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(new GuildJoinListener());
        builder.addEventListeners(new MessageReactListener());
        if (!DEBUG) builder.addEventListeners(new ReadyListener());

        CommandHandler.commands.put("bdays", new BdaysCommand());
        CommandHandler.commands.put("info", new InfoCommand());
        CommandHandler.commands.put("remind", new ReminderCommand());
        CommandHandler.commands.put("leaderboard", new LeaderboardCommand());

        builder.build();
        data.connect();


    }

}
