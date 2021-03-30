package de.lukkyz.lobsterbot;

import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.impl.*;
import de.lukkyz.lobsterbot.listeners.GuildJoinListener;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import de.lukkyz.lobsterbot.listeners.VoiceChatChangeListener;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import de.lukkyz.lobsterbot.utils.managers.BlacklistManager;
import de.lukkyz.lobsterbot.utils.managers.BotManager;
import de.lukkyz.lobsterbot.utils.managers.ExperienceManager;
import de.lukkyz.lobsterbot.utils.managers.UserManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Lobsterbot {

    public static LobsterDatabase database;
    public static UserManager userManager;
    public static ExperienceManager experienceManager;
    public static BotManager botManager;
    public static BlacklistManager blacklistManager;

    /* Bot Variables*/
    public static final double VERSION = 1.5D;
    public static final String PREFIX = "!";
    public static final int BUILD = 4;
    private static final boolean DEBUG = false;

    public static void main(String[] args) {

        JDABuilder builder = JDABuilder.createLight(botManager.getBotToken());

        database = new LobsterDatabase();
        experienceManager = new ExperienceManager(database);
        userManager = new UserManager(database);
        botManager = new BotManager(database);
        blacklistManager = new BlacklistManager(database);

        builder.setToken(botManager.getBotToken());
        builder.setStatus(DEBUG ? OnlineStatus.DO_NOT_DISTURB : OnlineStatus.ONLINE);
        builder.setAutoReconnect(true);
        builder.setActivity(DEBUG ? Activity.playing("in debug mode") : Activity.listening("commands"));
        builder.setMaxReconnectDelay(32);

        /* Register Event Listeners */
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(new GuildJoinListener());
        builder.addEventListeners(new VoiceChatChangeListener());

        /* Add Command Handlers */
        CommandHandler.commands.put("info", new InfoCommand());
        CommandHandler.commands.put("bdays", new BdaysCommand());
        CommandHandler.commands.put("remind", new ReminderCommand());
        CommandHandler.commands.put("exp", new EXPCommand());
        CommandHandler.commands.put("leaderboard", new LeaderboardCommand());
        CommandHandler.commands.put("donate", new DonateCommand());
        CommandHandler.commands.put("admin", new AdminCommand());
        CommandHandler.commands.put("color", new ColorCommand());

        LobsterDatabase.connect();

        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

}
