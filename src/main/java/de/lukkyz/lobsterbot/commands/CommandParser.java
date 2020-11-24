package de.lukkyz.lobsterbot.commands;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandParser {

    public static int count = 1;

    public CommandContainer parse(String raw, MessageReceivedEvent event) {

        String beheaded = raw.replaceFirst(Lobsterbot.PREFIX, "");
        String[] splitBeheaded = beheaded.split(" ");
        String invoke = splitBeheaded[0];
        ArrayList<String> split = new ArrayList<>();
        for (String s : splitBeheaded) {
            split.add(s);
        }
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        String name = event.getAuthor().getName(), g_name = event.getGuild().getName(), id = event.getMessageId(), c_name = event.getTextChannel().getName();
        if (CommandHandler.commands.containsKey(invoke))
            System.out.println("> " + name + " executed command \"!" + invoke + "\" in " + g_name + " (" + id + "-" + c_name + ") #" + count);
        count++;

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, event);
    }

}
