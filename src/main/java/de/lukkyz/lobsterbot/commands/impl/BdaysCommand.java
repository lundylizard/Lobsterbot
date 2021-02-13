package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;

public class BdaysCommand implements Command {

    private final LobsterDatabase database = Lobsterbot.database;

    @Override
    public void action(@NotNull String[] args, @Nonnull MessageReceivedEvent event) {

        if (args.length == 0) {

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Lobster Gang Birthdays**").setColor(0xc078eb).setDescription(database.getBirthdays(event)).build()).queue();

        } else if (args.length >= 1) {

            // args[1] = @user; args[2] = month; args[3] = day

            if (Lobsterbot.userManager.isModerator(event.getMember())) {

                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length == 4) {
                        if (checkIfNumber(args[2]) && checkIfNumber(args[3])) {
                            if (Integer.parseInt(args[2]) < 13 && Integer.parseInt(args[2]) > 0) {
                                if (Integer.parseInt(args[3]) < 32 && Integer.parseInt(args[3]) > 0) {

                                    Member member = event.getMessage().getMentionedMembers().get(0);

                                    if (member != null) {
                                        if (!database.isInEXPDB(member)) {

                                            database.insertBirthday(member, Integer.parseInt(args[3]), Integer.parseInt(args[2]));
                                            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Successfully added member").setColor(Color.GREEN).setDescription("Successfully added member " + member.getAsMention() + " to the database.").build()).queue();

                                        } else {

                                            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("Member with the id `" + member.getId() + "` is already registered.").build()).queue();

                                        }

                                    } else {

                                        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("User mentioned is not on the server.").build()).queue();

                                    }

                                } else {

                                    event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("`" + args[3] + "` is not a valid day. \nUsage: `!bdays add [@user] [month] [day]`").build()).queue();

                                }


                            } else {

                                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("`" + args[2] + "` is not a valid month. \nUsage: `!bdays add [@user] [month] [day]`").build()).queue();

                            }

                        } else {

                            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("The day or month you've entered is not valid. \nUsage: `!bdays add [@user] [month] [day]`").build()).queue();

                        }


                    } else {

                        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("Insufficient arguments. \nUsage: `!bdays add [@user] [month] [day]`").build()).queue();

                    }

                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length == 2) {

                        Member member = event.getMessage().getMentionedMembers().get(0);

                        if (member != null) {
                            if (database.isInBirthdayDatabase(member)) {

                                database.deleteBirthday(member);
                                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setTitle("Successfully removed member").setDescription("Successfully removed member " + member.getAsMention() + " from the database.").build()).queue();


                            } else {

                                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("Member " + member.getAsMention() + " is not in the database.").build()).queue();

                            }

                        } else {

                            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("The user you have tried to remove is no longer on the server. Please notify a developer to remove them from the database.").build()).queue();

                        }


                    } else {

                        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("Insufficient arguments. \nUsage: `!bdays remove [@user]`").build()).queue();

                    }

                } else {

                    event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("Unknown arguments. \nUsage: `!bdays [add/remove]`").build()).queue();

                }

            } else {

                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("An Error occurred").setDescription("You do not have permissions to modify birthdays.").build()).queue();

            }

        }

    }

    private boolean checkIfNumber(String input) {

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;

    }

}
