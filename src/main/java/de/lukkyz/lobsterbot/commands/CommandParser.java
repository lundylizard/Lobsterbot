package de.lukkyz.lobsterbot.commands;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class CommandParser {

    public CommandContainer parse(String raw, MessageReceivedEvent event) {

        String beheaded = raw.replaceFirst(Lobsterbot.PREFIX, "");
        String[] splitBeheaded = beheaded.split(" ");
        String invoke = splitBeheaded[0].toLowerCase();
        ArrayList<String> split = new ArrayList<>();

        for (String s : splitBeheaded) {
            split.add(s);
        }

        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, event);

    }

}
