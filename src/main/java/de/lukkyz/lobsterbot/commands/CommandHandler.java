package de.lukkyz.lobsterbot.commands;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class CommandHandler {

    public static final CommandParser parser = new CommandParser();
    public static final HashMap<String, Command> commands = new HashMap<>();

    public static void handleCommand(@NotNull CommandContainer cmd) {

        if (commands.containsKey(Objects.requireNonNull(cmd).invoke)) {

            commands.get(cmd.invoke).action(cmd.args, cmd.event);

        }

    }

    @Contract(pure = true)
    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    public static void addCommand(String invoke, Command command) {
        commands.put(invoke, command);
    }

    public static Command getCommand(String invoke) {
        return commands.get(invoke);
    }

}
