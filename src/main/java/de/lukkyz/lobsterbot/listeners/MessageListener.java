package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    LobsterDatabase database = new LobsterDatabase();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if ((new Random().nextInt(100) < 5) && (event.getMessage().getContentRaw().toLowerCase().contains("lobster") || event.getMessage().getContentRaw().toLowerCase().contains("lobsters"))) {

            event.getTextChannel().sendMessage("I love lobsters.").queue();
            System.out.println("> Triggered \"I love Lobsters.\" easter egg in " + event.getMessage().getGuild().getName() + " (" + event.getMessage().getId() + ")");

        }

        if (event.getMessage().getContentRaw().startsWith(Lobsterbot.PREFIX) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            if (database.blacklistedUser(event.getAuthor().getIdLong())) {

                event.getAuthor().openPrivateChannel().queue(channel -> channel.sendMessage("Seems like you are blacklisted from using Lobster Bot...").queue());

            } else {

                CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContentRaw(), event));

            }

        }

        if (event.getMessage().getContentRaw().toLowerCase().replace(" ", "").contains("nigger") || event.getMessage().getContentRaw().toLowerCase().replace(" ", "").contains("nigga")) {

            event.getMessage().delete().queue();

        }

    }

}
