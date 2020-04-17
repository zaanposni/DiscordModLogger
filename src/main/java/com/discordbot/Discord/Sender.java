package com.discordbot.Discord;

import com.discordbot.Config.Config;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;

import java.util.logging.Logger;

public class Sender {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    public static void sendToAllLogChannels(Event event, MessageEmbed embed) {
        for (Object logChannelID : Config.getArray("log_to_channels")) {
            try {
                TextChannel channel = event.getJDA().getTextChannelById(logChannelID.toString());
                if (channel == null) {
                    LOGGER.warning("Channel \"" + logChannelID.toString() + "\" not found");
                    continue;
                }
                channel.sendMessage(embed).queue();
            } catch(NullPointerException ex) {
                LOGGER.warning("Channel \"" + logChannelID.toString() + "\" not found");
            }
        }
    }
}
