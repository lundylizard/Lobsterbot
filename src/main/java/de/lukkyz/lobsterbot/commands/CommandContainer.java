package de.lukkyz.lobsterbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContainer {

    public String raw, beheaded, invoke;
    public String[] splitBeheaded, args;
    public MessageReceivedEvent event;

    public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event) {

        this.raw = raw;
        this.beheaded = beheaded;
        this.splitBeheaded = splitBeheaded;
        this.invoke = invoke;
        this.args = args;
        this.event = event;

    }

}
