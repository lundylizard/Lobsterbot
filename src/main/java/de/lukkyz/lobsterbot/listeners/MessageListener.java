package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.CommandHandler;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MessageListener extends ListenerAdapter {

    LobsterDatabase database = new LobsterDatabase();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        Message message = event.getMessage();

        /* Remove bad words, todo add database stuff */
        if (message.getContentRaw().toLowerCase().replace(" ", "").contains("nigger") || message.getContentRaw().toLowerCase().replace(" ", "").contains("nigga")) {

            message.delete().queue();

        }

        /*Experience Management */
        if (!message.getContentRaw().startsWith(Lobsterbot.PREFIX) && !message.getAuthor().isBot()) {
            if (!Lobsterbot.database.isInEXPDB(event.getMember())) {

                Lobsterbot.database.createEXPDBEntry(event.getMember());
                Lobsterbot.experienceManager.addEXP(event.getMember(), 10);

            } else {

                List<Message> history = event.getTextChannel().getIterableHistory().complete().stream().limit(10).filter(msg -> !msg.equals(event.getMessage())).collect(Collectors.toList());
                int spam = history.stream().filter(messages -> messages.getAuthor().equals(event.getAuthor()) && !messages.getAuthor().isBot()).filter(msg -> (event.getMessage().getTimeCreated().toEpochSecond() - msg.getTimeCreated().toEpochSecond()) < 6).collect(Collectors.toList()).size();

                if (!(spam > 2)) {

                    Lobsterbot.experienceManager.addEXP(event.getMember(), 15);

                    if (new Random().nextInt(100) < 3) {

                        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription(event.getAuthor().getAsMention() + " has gained an additional 10 EXP.").build()).queue();
                        Lobsterbot.experienceManager.addEXP(event.getMember(), 10);

                    }

                }

                if ((Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.database.getLevelFromUser(event.getMember())) <= Lobsterbot.database.getEXPfromUser(event.getMember()))) {

                    Lobsterbot.database.setLevelFromUser(event.getMember(), Lobsterbot.database.getLevelFromUser(event.getMember()) + 1);
                    Lobsterbot.database.addEXPToUser(event.getMember(), 0);
                    event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Level Up! :sparkles:").setDescription(event.getAuthor().getAsMention() + " is now level " + Lobsterbot.database.getLevelFromUser(event.getMember()) + ".").build()).queue();

                }

            }
        }

        /* Lobster Easter Egg */
        if ((new Random().nextInt(100) < 2) && (message.getContentRaw().toLowerCase().contains("lobster") || message.getContentRaw().toLowerCase().contains("lobsters"))) {

            event.getTextChannel().sendMessage("I love lobsters.").queue();
            System.out.println("> Triggered \"I love Lobsters.\" easter egg in " + message.getGuild().getName() + " (" + message.getId() + ")");

        }

        /* Command Listener */
        if (message.getContentRaw().startsWith(Lobsterbot.PREFIX) && message.getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            if (database.blacklistedUser(event.getAuthor().getIdLong())) {

                event.getAuthor().openPrivateChannel().queue(channel -> channel.sendMessage("Seems like you are blacklisted from using Lobster Bot...").queue());

            } else {

                CommandHandler.handleCommand(CommandHandler.parser.parse(message.getContentRaw(), event));

            }

        }



    }

}
