package com.discordbot.Discord;

import com.discordbot.Config.Config;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;

import java.io.IOException;
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

    public static void sendToAllWebhhok(String message, String eventName) {
        if (!Config.getArray("log_events_to_discord_webhook").contains(eventName))
            return;
        for (Object webhookURL : Config.getArray("log_discord_webhook_urls")) {
            WebhookHandler webhook = new WebhookHandler(webhookURL.toString());
            webhook.setContent(message);
            try {
                webhook.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
