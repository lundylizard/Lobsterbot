package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import de.lukkyz.lobsterbot.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class BdaysCommand implements Command {

    private LobsterDatabase database = new LobsterDatabase();

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        Role lobsterroledebug = event.getGuild().getRoleById("797546283606212719");
        Role lobsterrole = event.getGuild().getRoleById("621077872701997062");

        if (args.length == 0) {

            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("**Lobster Gang Birthdays**").setColor(Color.RED).setDescription(database.getBdays()).build()).queue();

        } else if (args.length <= 4 && args.length > 0) {

            if (event.getGuild().getMember(event.getAuthor()).getRoles().contains(lobsterroledebug) || event.getGuild().getMember(event.getAuthor()).getRoles().contains(lobsterrole)) {

                try {

                    if (args[0].equalsIgnoreCase("add") && args.length > 3 && checkIfNumber(args[3]) && checkIfNumber(args[2])) {

                        event.getTextChannel().sendMessage("Added user to birthday database.").queue();
                        database.insertBday(0, Integer.parseInt(args[3]), Integer.parseInt(args[2]), args[1].replace("_", " "));

                    } else if (args[0].equalsIgnoreCase("remove") && args.length > 1) {

                        event.getTextChannel().sendMessage("Removed user from birthday database.").queue();
                        database.deleteBday(args[1].replace("_", " "));

                    } else if (args.length < 4) {

                        event.getTextChannel().sendMessage(Utils.generateHelpString("`!bdays [add/remove] [name] (day month)`")).queue();

                    } else {
                        event.getTextChannel().sendMessage(Utils.generateHelpString("`!bdays [add/remove] [name] (day month)`")).queue();
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    event.getTextChannel().sendMessage(Utils.generateHelpString("`!bdays [add/remove] [name] (day month)`")).queue();
                    return;
                } catch (NumberFormatException e) {
                    event.getTextChannel().sendMessage("Words don't count, I only want to see numbers. `(Please enter a valid value)`").queue();
                    return;
                }

            } else {

                event.getTextChannel().sendMessage("Seems like you lack permission to edit birthdays, sad!").queue();

            }

        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
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
