package com.discordbot.Listener;

import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.MessageCacher;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.WarningLogEmbed;
import com.discordbot.Meta.VersionInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageUpdate extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent event) {
        if (Config.getArray("exclude_channels_from_log").contains(event.getChannel().getId()))
            return; // do not log these channels

        if (event.getAuthor() == event.getJDA().getSelfUser())
            return; // do not log self

        if (event.getMessage().getType() != MessageType.DEFAULT || event.getChannelType() != ChannelType.TEXT)
            return; // do not log system messages and DMs

        LOGGER.info("Received Message update.");

        // Get old from cache
        Message oldMessage = MessageCacher.getMessageByID(event.getMessage().getId());
        if (oldMessage != null) {
            LOGGER.info("Found old version of message in cache.");
        }
        // Cache new
        MessageCacher.addMessage(event.getMessage());

        // Log
        EmbedBuilder embed = new WarningLogEmbed();
        Message message = event.getMessage();
        User author = event.getAuthor();
        embed.setAuthor(author.getName(),event.getMessage().getJumpUrl(), author.getAvatarUrl());
        embed.setDescription("**Message by " + author.getAsMention() + " edited in " + "<#" + event.getChannel().getId() + ">.** [Jump](" + message.getJumpUrl() + ")");
        if (oldMessage != null) {
            if (oldMessage.getContentRaw().isEmpty()) {
                embed.addField("**Old message**", "No content.", false);
            } else {
                embed.addField("**Old message**", oldMessage.getContentRaw().substring(0, Math.min(1024, oldMessage.getContentRaw().length())), false);
                if (oldMessage.getContentRaw().length() > 1024) {
                    embed.addField("**Second part of old message**", oldMessage.getContentRaw().substring(1024), false);
                }
            }
        }
        embed.addField("**Updated message**", message.getContentRaw().substring(0, Math.min(1024, message.getContentRaw().length())), false);
        if (message.getContentRaw().length() > 1024) {
            embed.addField("**Second part of updated message**", message.getContentRaw().substring(1024), false);
        }
        if (!message.getEmbeds().isEmpty()) {
            embed.addField("**Embeds**", "Found " + message.getEmbeds().size() + " embed(s). Not displayable for version " + VersionInfo.getLongVersion(), false);
        }
        if (!message.getAttachments().isEmpty()) {
            embed.setImage(message.getAttachments().get(0).getProxyUrl()); // most likely there is only one image so it will be displayed on the embed
            StringBuilder attachments = new StringBuilder();
            for (Message.Attachment attachment: message.getAttachments()) {
                String url = attachment.getProxyUrl();
                String downloadURL = "https://cdn.discordapp.com" + url.substring(url.indexOf("/attachments/"));
                String filename = attachment.getFileName();
                attachments.append("[").append(filename).append("](").append(downloadURL).append(")\n");
            }
            embed.addField("**Uploaded files**", attachments.toString(), false);
        }
        embed.setFooter("UserID: " + author.getId() + " | " + UniqueIDHandler.getNewUUID() + " | MessageUpdate");
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
