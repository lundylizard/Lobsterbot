package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class DonateCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String output = "";

        output += "If you want to support me and my development, consider donating.\n";
        output += "Donations go into server costs, new equipment & monster energy.\n\n";
        output += "**PayPal:** https://paypal.me/Lukkyz1337\n";
        output += "*More to come soon.*";

        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.CYAN).setTitle("Donate to lundylizard").setDescription(output).build()).queue();

    }

}
