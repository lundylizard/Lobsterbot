package de.lukkyz.lobsterbot.commands;

import de.lukkyz.lobsterbot.Lobsterbot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class CommandHandler {

    public static final CommandParser parser = new CommandParser();
    public static final HashMap<String, Command> commands = new HashMap<>();

    public static void handleCommand(@NotNull CommandContainer cmd) {

        if (commands.containsKey(Objects.requireNonNull(cmd).invoke)) {

            commands.get(cmd.invoke).action(cmd.args, cmd.event);
            Lobsterbot.botManager.addExecutedCommands(1);

        }

    }

}


