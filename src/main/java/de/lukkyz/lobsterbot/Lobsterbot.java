package de.lukkyz.lobsterbot;

import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.impl.*;
import de.lukkyz.lobsterbot.listeners.GuildJoinListener;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import de.lukkyz.lobsterbot.listeners.MessageReactListener;
import de.lukkyz.lobsterbot.listeners.VoiceChatChangeListener;
import de.lukkyz.lobsterbot.utils.ExperienceManager;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Lobsterbot {

    public static final boolean DEBUG = true;

    public static LobsterDatabase database;
    public static JDABuilder builder;
    public static ExperienceManager experienceManager;

    /* Bot Variables*/
    public static final String PREFIX = "!";

    public static void main(String[] args) throws LoginException {

        builder = new JDABuilder(AccountType.BOT);
        database = new LobsterDatabase();
        experienceManager = new ExperienceManager();

        builder.setToken(database.getBotToken());
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setAutoReconnect(true);
        builder.setActivity(DEBUG ? Activity.playing("in debug mode.") : Activity.listening("commands."));

        /* Register Event Listeners */
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(new GuildJoinListener());
        builder.addEventListeners(new MessageReactListener());
        builder.addEventListeners(new VoiceChatChangeListener());

        CommandHandler.commands.put("bdays", new BdaysCommand());
        CommandHandler.commands.put("info", new InfoCommand());
        CommandHandler.commands.put("remind", new ReminderCommand());
        CommandHandler.commands.put("exp", new EXPCommand());
        CommandHandler.commands.put("leaderboard", new LeaderboardCommand());

        builder.build();
        database.connect();

    }

}
