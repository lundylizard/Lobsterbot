package de.lukkyz.lobsterbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContainer {

    public final String raw, beheaded, invoke;
    public final String[] splitBeheaded, args;
    public final MessageReceivedEvent event;

    public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event) {
        this.raw = raw;
        this.beheaded = beheaded;
        this.splitBeheaded = splitBeheaded;
        this.invoke = invoke;
        this.args = args;
        this.event = event;
    }

}