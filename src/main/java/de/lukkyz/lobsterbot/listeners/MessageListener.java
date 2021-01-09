package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    public static int count = 0;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if ((new Random().nextInt(100) < 5) && (event.getMessage().getContentRaw().contains("lobster") || event.getMessage().getContentRaw().contains("lobsters"))) {
            event.getTextChannel().sendMessage("I love lobsters.").queue();
            System.out.println("> Triggered \"I love Lobsters.\" easter egg in " + event.getMessage().getGuild().getName() + " (" + event.getMessage().getId() + ")");
        }

        if (event.getMessage().getContentRaw().startsWith(Lobsterbot.PREFIX) &&
                event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContentRaw(), event));
        }

        count++;

    }
}
