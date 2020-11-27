package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.Lobsterbot;
import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BdaysCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        try {

            if (args.length < 1) {

                ResultSet results = Lobsterbot.data.getBdaysExperimental();
                String id = results.getString("discord_id");
                int day = results.getInt("day");
                int month = results.getInt("month");
                String name = results.getString("name");

                String str_month = "";

                switch (month) {
                    case 1:
                        str_month = "Janurary";
                        break;
                    case 2:
                        str_month = "Februrary";
                        break;
                    case 3:
                        str_month = "March";
                        break;
                    case 4:
                        str_month = "April";
                        break;
                    case 5:
                        str_month = "May";
                        break;
                    case 6:
                        str_month = "June";
                        break;
                    case 7:
                        str_month = "July";
                        break;
                    case 8:
                        str_month = "August";
                        break;
                    case 9:
                        str_month = "September";
                        break;
                    case 10:
                        str_month = "October";
                        break;
                    case 11:
                        str_month = "November";
                        break;
                    case 12:
                        str_month = "December";
                        break;
                }


                event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Lobster Gang Birthdays**").setColor(Color.RED).setDescription(str_month).build()).queue();


            } else {

                if (args[1] == "add") {


                }

            }

        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

}
