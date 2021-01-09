package de.lukkyz.lobsterbot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public static String generateEmote() {

        List<String> emotes = new ArrayList<>();
        emotes.add(":heart:");
        emotes.add(":pleading_face:");
        emotes.add(":flushed:");
        emotes.add(":yum:");
        emotes.add(":eyes:");
        emotes.add(":yawning_face:");
        emotes.add(":v:");
        emotes.add("");
        emotes.add("");
        emotes.add("");
        emotes.add("");

        return emotes.get(new Random().nextInt(emotes.size()));

    }

    public static boolean isMod(Member member) {
        List<Role> roles = member.getGuild().getRoles();

        for (int i = 0; i < roles.size(); i++) {

            //if (roles.contains(member.getRoles().)) {

        }

    }

}

}
