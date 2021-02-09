package de.lukkyz.lobsterbot.commands;

import java.util.HashMap;

public class CommandHandler {

    public static final CommandParser parser = new CommandParser();
    public static HashMap<String, Command> commands = new HashMap<>();

    public static void handleCommand(CommandContainer cmd) {

        if (commands.containsKey(cmd.invoke)) {

                commands.get(cmd.invoke).action(cmd.args, cmd.event);

            }

        }

    }


