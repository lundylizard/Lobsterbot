package de.lukkyz.lobsterbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class CustomEmbeds {

    public MessageEmbed bdaysEmbed(String input) {
        return new EmbedBuilder().setTitle("**Official Lobster Gang Birthdays**").setColor(Color.RED).setDescription(input).build();
    }

}
