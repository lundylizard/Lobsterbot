package de.lukkyz.lobsterbot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class VoiceChatChangeListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {

        TextChannel voiceChannel = event.getGuild().getTextChannelById(621441669539299328L);
        if (voiceChannel != null)
            voiceChannel.sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription(event.getEntity().getEffectiveName() + " joined the voice chat.").build()).queue();

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {

        TextChannel voiceChannel = event.getGuild().getTextChannelById(621441669539299328L);
        if (voiceChannel != null)
            voiceChannel.sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription(event.getEntity().getEffectiveName() + " left the voice chat.").build()).queue();

    }
}
