package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {

        for (Guild guilds : event.getJDA().getGuilds()) {
            for (Member members : guilds.getMembers()) {
                if (members.isOwner()) {
                    members.getUser().openPrivateChannel().queue(channel ->
                            channel.sendMessage("Hey... :point_right: :point_left: \nSeems like I was unavailable for a second there.\nI'm back now. Are you fine? " + Utils.generateEmote())
                                    .queue(message -> message.delete().queueAfter(1, TimeUnit.MINUTES)));
                }
            }
        }
    }
}
