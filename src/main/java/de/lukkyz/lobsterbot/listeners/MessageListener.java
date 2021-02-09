package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.CommandHandler;
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

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        Message message = event.getMessage();

        /* Command Listener */
        if (message.getContentRaw().startsWith(Lobsterbot.PREFIX) && !message.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {

            CommandHandler.handleCommand(CommandHandler.parser.parse(message.getContentRaw(), event));
            System.out.println("> " + event.getAuthor().getName() + " executed command " + message.getContentRaw().toLowerCase());

        }

        /*Experience Management */
        if (!message.getContentRaw().startsWith(Lobsterbot.PREFIX) && !message.getAuthor().isBot()) {
            if (!Lobsterbot.database.isInEXPDB(event.getMember())) {

                Lobsterbot.database.createEXPDBEntry(event.getMember());
                System.out.println("> Created EXP database entry for " + event.getMember().getUser().getName());
                Lobsterbot.experienceManager.addEXP(event.getMember(), 50);
                Lobsterbot.experienceManager.addOverallEXP(event.getMember(), 50);
                System.out.println("> " + event.getAuthor().getName() + " gained 50 EXP (EXP needed for next level: " + Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) + ")");

            } else {

                List<Message> history = event.getTextChannel().getIterableHistory().complete().stream().limit(10).filter(msg -> !msg.equals(event.getMessage())).collect(Collectors.toList());
                int spam = history.stream().filter(messages -> messages.getAuthor().equals(event.getAuthor()) && !messages.getAuthor().isBot()).filter(msg -> (event.getMessage().getTimeCreated().toEpochSecond() - msg.getTimeCreated().toEpochSecond()) < 6).collect(Collectors.toList()).size();

                if (!(spam > 2)) {

                    Lobsterbot.experienceManager.addEXP(event.getMember(), 50);
                    Lobsterbot.experienceManager.addOverallEXP(event.getMember(), 50);
                    System.out.println("> " + event.getAuthor().getName() + " gained 50 EXP (EXP needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + ")");

                    if (new Random().nextInt(1000) <= 50) {

                        int random = ((new Random().nextInt(50) * 10) + 10);
                        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription(event.getAuthor().getAsMention() + " has gained an additional **" + random + "** EXP.").build()).queue();
                        Lobsterbot.experienceManager.addEXP(event.getMember(), random);
                        Lobsterbot.experienceManager.addOverallEXP(event.getMember(), random);
                        System.out.println("> " + event.getAuthor().getName() + " gained " + random + " EXP (EXP needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + ")");

                    }

                } else {

                    Lobsterbot.experienceManager.addEXP(event.getMember(), 10);
                    Lobsterbot.experienceManager.addOverallEXP(event.getMember(), 10);
                    System.out.println("> " + event.getAuthor().getName() + " gained 10 EXP (EXP needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + ")");

                }

                if ((Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) <= Lobsterbot.experienceManager.getEXP(event.getMember()))) {

                    Lobsterbot.experienceManager.addLevel(event.getMember(), 1);
                    Lobsterbot.experienceManager.setEXP(event.getMember(), 0);
                    event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Level Up! :sparkles:").setDescription(event.getAuthor().getAsMention() + " is now level " + Lobsterbot.experienceManager.getLevel(event.getMember()) + "!").build()).queue();
                    System.out.println("> " + event.getAuthor().getName() + " leveled up! (EXP needed for next level: " + Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) + ")");


                }

            }
        }

        /* Lobster Easter Egg */
        if ((new Random().nextInt(100) < 2) && (message.getContentRaw().toLowerCase().contains("lobster") || message.getContentRaw().toLowerCase().contains("lobsters"))) {

            event.getTextChannel().sendMessage("I love lobsters.").queue();
            System.out.println("> " + event.getAuthor().getName() + " triggered \"I love lobsters.\" easter egg in " + message.getGuild().getName() + " (" + event.getTextChannel().getName() + ")");

        }

    }

}
