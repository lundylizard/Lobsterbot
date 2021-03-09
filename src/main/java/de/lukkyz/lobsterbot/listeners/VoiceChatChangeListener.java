package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
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

        if (!(Lobsterbot.blacklistManager.userBlacklisted(event.getMember()))) {

            final TextChannel voiceChannel = event.getGuild().getTextChannelById(621441669539299328L);

            if (voiceChannel != null && !(event.getEntity().getUser().isBot())) {

                voiceChannel.sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription(Lobsterbot.botManager.formatToEmoteMessage(event.getEntity().getEffectiveName(), event.getEntity()) + " joined the voice chat.").build()).queue();
                System.out.println("> " + event.getEntity().getUser().getName() + " joined the voice chat.");

            }

        }

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {

        if (!(Lobsterbot.blacklistManager.userBlacklisted(event.getMember()))) {

            final TextChannel voiceChannel = event.getGuild().getTextChannelById(621441669539299328L);

            if (voiceChannel != null && !(event.getEntity().getUser().isBot())) {

                voiceChannel.sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription(Lobsterbot.botManager.formatToEmoteMessage(event.getEntity().getEffectiveName(), event.getEntity()) + " left the voice chat.").build()).queue();
                System.out.println("> " + event.getEntity().getUser().getName() + " left the voice chat.");

            }

        }

    }

}
