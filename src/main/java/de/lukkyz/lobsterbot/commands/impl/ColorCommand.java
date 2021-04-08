package de.lukkyz.lobsterbot.commands.impl;

import de.lukkyz.lobsterbot.commands.Command;
import de.lukkyz.lobsterbot.utils.LobsterDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class ColorCommand implements Command {

    private LobsterDatabase database;

    public ColorCommand(LobsterDatabase database) {
        this.database = database;
    }

    @Override
    public void action(@NotNull String[] args, MessageReceivedEvent event) throws IllegalStateException {

        //!color <role, #hex> [name]
        //-> returns role name
        //-> sets color to hex

        if (args.length == 0) {

            if (database.isInColorDB(event.getMember())) {

                String hexColor = String.format("%06X", (0xFFFFFF & event.getMember().getColor().getRGB()));
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(database.getColorFromUser(event.getMember())).setDescription("Your set color is currently **" + hexColor + "**.\n\nTo change your color again use `!role <color-in-hex>`\nTo change the name of the color role use `!color name <name>`").setThumbnail("https://dummyimage.com/64x64/" + hexColor + "/" + hexColor + ".png").build()).queue();

            } else {

                event.getTextChannel().sendMessage("To change your color use `!role <color-in-hex>`").queue();

            }

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("role")) {

                event.getTextChannel().sendMessage("To rename your color role, please use `!color role [name]`").queue();

            } else {

                //change color
                if (!args[0].startsWith("#")) args[0] = "#" + args[0];

                if (args[0].matches("^[#]([0-9A-Fa-f]{2})+$")) { // valid hex, including "#" at the beginning

                    if (database.isInColorDB(event.getMember())) {

                        Role role = event.getGuild().getRoleById(database.getColorRoleIdFromUser(event.getMember()));

                        if (role != null) {

                            role.getManager().setColor(Color.decode(args[0])).queue();
                            database.changeColorFromUser(event.getMember(), Color.decode(args[0]));

                            if (!event.getMember().getRoles().contains(role)) {

                                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(database.getColorRoleIdFromUser(event.getMember()))).queue();

                            }

                            String hexColor = String.format("%06X", (0xFFFFFF & Color.decode(args[0]).getRGB()));
                            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Successfully changed color").setColor(Color.decode("#" + hexColor)).setDescription("You have changed your color. Your color is now **" + args[0] + "**.\n\nTo change your color again use `!role <color-in-hex>`\nTo change the name of the color role use `!color name <name>`").setThumbnail("https://dummyimage.com/64x64/" + hexColor + "/" + hexColor + ".png").build()).queue();

                        } else throw new IllegalStateException("Tried to change color of role that doesn't exist.");

                    } else {

                        Role role2 = event.getGuild().createRole().setName(event.getAuthor().getName()).setColor(Role.DEFAULT_COLOR_RAW).setMentionable(false).complete();
                        event.getGuild().addRoleToMember(event.getMember(), role2).queue();
                        event.getGuild().getRoleById(role2.getIdLong()).getManager().setColor(Color.decode(args[0])).queue();
                        List<Role> roles = event.getGuild().getRoles();

                        for (Role role : roles) {
                            if (role.getName().equalsIgnoreCase(event.getAuthor().getName())) {

                                database.createUserInColorDB(event.getMember(), Color.decode(args[0]).getRGB(), event.getAuthor().getName(), role.getIdLong());

                            }
                        }

                        String hexColor = String.format("%06X", (0xFFFFFF & Color.decode(args[0]).getRGB()));
                        event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Successfully changed color").setColor(Color.decode("#" + hexColor)).setDescription("You have changed your color. Your color is now **" + args[0] + "**.\n\nTo change your color again use `!role <color-in-hex>`\nTo change the name of the color role use `!color name <name>`").setThumbnail("https://dummyimage.com/64x64/" + hexColor + "/" + hexColor + ".png").build()).queue();

                    }

                } else {

                    event.getTextChannel().sendMessage("Invalid hex format. Please use `#RRGGBB`").queue();

                }

            }

        } else if (args.length >= 2) {

            String name = "";

            for (int i = 1; i < args.length; i++) name += args[i] + " ";

            if (name.length() <= 33) {
                if (name.length() >= 5) {
                    if (database.isInColorDB(event.getMember())) {

                        database.setNameOfRoleFromUser(event.getMember(), name);
                        event.getGuild().getRoleById(database.getColorRoleIdFromUser(event.getMember())).getManager().setName(name).complete();
                        event.getTextChannel().sendMessage("Successfully changed name of your color role to **" + name.substring(0, name.length() - 1) + "**.").queue();

                    } else throw new IllegalStateException("Tried to change name of role that doesn't exist.");


                } else {

                    event.getTextChannel().sendMessage("Entered role name is too short. Minimum characters: **4**").queue();

                }

            } else {

                event.getTextChannel().sendMessage("Entered role name is too long. Maximal characters: **32**").queue();

            }

        }

    }

}
