package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DonateCommand implements Command {

    @Override
    public void action(String[] args, @NotNull MessageReceivedEvent event) {

        String output = "";

        output += "*Credits go to Mcfluff (Birthday Suffix) & Lu (Progress Bar).*\n\n";
        output += "If you want to support me and my development, consider donating.\n";
        output += "Donations flow into server costs, new equipment & energy drinks.\n\n";
        output += "**PayPal:** https://paypal.me/Lukkyz1337\n";

        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.CYAN).setTitle("Donate to lundylizard").setDescription(output).build()).queue();

    }

}
