package de.lukkyz.lobsterbot.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {

        List<Guild> guilds = event.getJDA().getGuilds();

        for (Guild guild : guilds) {
            System.out.println("Running on " + guild.getName());
        }

    }

}
