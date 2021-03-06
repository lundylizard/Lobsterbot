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
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (!(Lobsterbot.blacklistManager.userBlacklisted(Objects.requireNonNull(event.getMember())))) {

            final Message message = event.getMessage();
            Lobsterbot.botManager.addSentMessages(1);
            Lobsterbot.userManager.addMessagesSent(event.getMember(), 1);

            /* Command Listener */

            if (message.getContentRaw().startsWith(Lobsterbot.PREFIX) && !message.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {

                CommandHandler.handleCommand(CommandHandler.parser.parse(message.getContentRaw(), event));
                System.out.println("> " + event.getAuthor().getName() + " executed command " + message.getContentRaw().toLowerCase());

            }

            /*Experience Management */

            if (!message.getContentRaw().startsWith(Lobsterbot.PREFIX) && !message.getAuthor().isBot() && event.getGuild().getIdLong() != 705938723824730122L && !event.getTextChannel().getName().equalsIgnoreCase("serious")) {

                // EXP Variables
                int exp = (int) (50 * Lobsterbot.experienceManager.getEXPMultiplier());
                int chance = Lobsterbot.experienceManager.getAdditionalEXPChance();

                if (!Lobsterbot.database.isInEXPDB(event.getMember())) { // If user is not in EXP Database

                    Lobsterbot.database.createEXPDBEntry(event.getMember());
                    System.out.println("> Created EXP database entry for " + event.getMember().getUser().getName());
                    Lobsterbot.experienceManager.addEXP(event.getMember(), exp);
                    Lobsterbot.experienceManager.addOverallEXP(event.getMember(), exp);
                    System.out.println("> " + event.getAuthor().getName() + " gained " + exp + " EXP (EXP needed for next level: " + Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) + ")");

                } else {

                    // Get message history and convert into spam int
                    List<Message> history = event.getTextChannel().getIterableHistory().complete().stream().limit(10).filter(msg -> !msg.equals(event.getMessage())).collect(Collectors.toList());
                    int spam = history.stream().filter(messages -> messages.getAuthor().equals(event.getAuthor()) && !messages.getAuthor().isBot()).filter(msg -> (event.getMessage().getTimeCreated().toEpochSecond() - msg.getTimeCreated().toEpochSecond()) < 6).collect(Collectors.toList()).size();

                    if (!(spam >= 2)) {

                        Lobsterbot.experienceManager.addEXP(event.getMember(), exp);
                        Lobsterbot.experienceManager.addOverallEXP(event.getMember(), exp);
                        System.out.println("> " + event.getAuthor().getName() + " gained " + exp + " EXP (EXP needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + ")");

                        // Additional EXP
                        if (new Random().nextInt(1000) <= chance) {

                            int random = (int) (((new Random().nextInt(50) * 10) + 10) * Lobsterbot.experienceManager.getEXPMultiplier());
                            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription(event.getAuthor().getAsMention() + " has gained an additional **" + random + "** EXP").build()).queue();
                            Lobsterbot.experienceManager.addEXP(event.getMember(), random);
                            Lobsterbot.experienceManager.addOverallEXP(event.getMember(), random);
                            System.out.println("> " + event.getAuthor().getName() + " gained " + random + " EXP (EXP needed for next level: " + (Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember())) + ")");

                        }

                    }

                    // Level Up
                    while ((Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) <= Lobsterbot.experienceManager.getEXP(event.getMember()))) {

                        Lobsterbot.experienceManager.setEXP(event.getMember(), Math.abs((Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) - Lobsterbot.experienceManager.getEXP(event.getMember()))));
                        Lobsterbot.experienceManager.addLevel(event.getMember(), 1);
                        event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Level Up! :sparkles:").setDescription(event.getAuthor().getAsMention() + " is now level " + Lobsterbot.experienceManager.getLevel(event.getMember()) + "!").build()).queue();
                        System.out.println("> " + event.getAuthor().getName() + " leveled up! (EXP needed for next level: " + Lobsterbot.experienceManager.calculateEXPneeded(Lobsterbot.experienceManager.getLevel(event.getMember())) + ")");

                    }

                }

            }

            /* Lobster Easter Egg */

            if ((new Random().nextInt(1000) <= 20) && (message.getContentRaw().toLowerCase().contains("lobster") || message.getContentRaw().toLowerCase().contains("lobsters"))) {

                event.getTextChannel().sendMessage("I love lobsters.").queue();
                System.out.println("> " + event.getAuthor().getName() + " triggered \"I love lobsters.\" easter egg in " + message.getGuild().getName() + " (#" + event.getTextChannel().getName() + ")");

            }

            /* Fluff Counter */

            if (event.getMessage().getContentRaw().equalsIgnoreCase("fluff++")) {

                Lobsterbot.botManager.addCounter("fluff", 1);
                event.getTextChannel().sendMessage("> Fluff has said that he's not gay " + Lobsterbot.botManager.getCounter("fluff") + " times so far.").queue();

            }

            if (event.getMessage().getContentRaw().equalsIgnoreCase("fluff--")) {

                Lobsterbot.botManager.subCounter("fluff", 1);
                event.getTextChannel().sendMessage("> Fluff has said that he's not gay " + Lobsterbot.botManager.getCounter("fluff") + " times so far.").queue();

            }

            /* funny kanye gif */

            if (event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getAsMention())) {

                event.getTextChannel().sendMessage("https://media.discordapp.net/attachments/548357641898819584/778788017770856478/kanye.gif").queue();

            }

        }

    }

}
