package de.lukkyz.lobsterbot;

import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.commands.impl.*;
import de.lukkyz.lobsterbot.listeners.GuildJoinListener;
import de.lukkyz.lobsterbot.listeners.MessageListener;
import de.lukkyz.lobsterbot.listeners.RoleAddedEvent;
import de.lukkyz.lobsterbot.listeners.VoiceChatChangeListener;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import de.lukkyz.lobsterbot.utils.managers.BlacklistManager;
import de.lukkyz.lobsterbot.utils.managers.BotManager;
import de.lukkyz.lobsterbot.utils.managers.ExperienceManager;
import de.lukkyz.lobsterbot.utils.managers.UserManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Lobsterbot extends Thread {

    public static LobsterDatabase database;
    public static UserManager userManager;
    public static ExperienceManager experienceManager;
    public static BotManager botManager;
    public static BlacklistManager blacklistManager;

    /* Bot Variables*/
    public static final String PREFIX = "!";
    private static final boolean DEBUG = true;

    public static void main(String[] args) {
        new Lobsterbot().start();
    }

    @Override
    public void run() {

        JDABuilder builder = new JDABuilder(AccountType.BOT);

        database = new LobsterDatabase();
        experienceManager = new ExperienceManager();
        userManager = new UserManager();
        botManager = new BotManager();
        blacklistManager = new BlacklistManager();

        builder.setToken(botManager.getBotToken());
        builder.setStatus(DEBUG ? OnlineStatus.IDLE : OnlineStatus.ONLINE);
        builder.setAutoReconnect(true);
        builder.setActivity(DEBUG ? Activity.playing("in debug mode.") : Activity.listening("commands."));

        /* Register Event Listeners */
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(new GuildJoinListener());
        builder.addEventListeners(new VoiceChatChangeListener());
        builder.addEventListeners(new RoleAddedEvent());

        /* Add Command Handlers */
        CommandHandler.commands.put("info", new InfoCommand());
        CommandHandler.commands.put("bdays", new BdaysCommand());
        CommandHandler.commands.put("remind", new ReminderCommand());
        CommandHandler.commands.put("exp", new EXPCommand());
        CommandHandler.commands.put("leaderboard", new LeaderboardCommand());
        CommandHandler.commands.put("donate", new DonateCommand());

        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        LobsterDatabase.connect();

    }

}
