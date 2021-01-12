package de.lukkyz.lobsterbot.listeners;

import de.lukkyz.lobsterbot.Lobsterbot;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        long starred = event.getTextChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor().getIdLong();
        MessageReaction.ReactionEmote emote = event.getReaction().getReactionEmote();

        if ((emote.getName().hashCode() == 11088) && !(starred == event.getUser().getIdLong())) {
            if (!Lobsterbot.data.isInStarsLeaderboard(Long.toString(starred))) {

                Lobsterbot.data.createStarDatabase(Long.toString(starred));
                System.out.println("> Created database entry for " + starred + " in ´stars´.");
                Lobsterbot.data.addStarsToUser(Long.toString(starred), 1);
                System.out.println("> " + event.getUser().getName() + " awarded star to " + starred);

            } else {

                Lobsterbot.data.addStarsToUser(Long.toString(starred), Lobsterbot.data.getStarsFromUser(Long.toString(starred)) + 1);
                System.out.println("> " + event.getUser().getName() + " awarded star to " + starred);

            }
        }
    }
}
