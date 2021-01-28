package com.discordbot.Listener;

import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.MessageCacher;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.FailureLogEmbed;
import com.discordbot.Meta.VersionInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageDelete extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
        if (Config.getArray("exclude_channels_from_log").contains(event.getChannel().getId()))
            return; // do not log these channels

        if (event.getChannelType() != ChannelType.TEXT)
            return; // do not log system messages and DMs

        LOGGER.info("Received Message deletion.");

        // Get old and delete from cache
        Message oldMessage = MessageCacher.getMessageByID(event.getMessageId());
        MessageCacher.removeMessageByID(event.getMessageId());

        EmbedBuilder embed = new FailureLogEmbed();
        if (oldMessage != null) {
            LOGGER.info("Found old version of message in cache.");
            embed.setAuthor(oldMessage.getAuthor().getName(), oldMessage.getJumpUrl(), oldMessage.getAuthor().getAvatarUrl());
            embed.setDescription("**Message by " + oldMessage.getAuthor().getAsMention() + " deleted in <#" + event.getChannel().getId() + ">.**");
            if (!oldMessage.getContentRaw().isEmpty()) {
                embed.addField("**Deleted message**", oldMessage.getContentRaw().substring(0, Math.min(1024, oldMessage.getContentRaw().length())), false);
                if (oldMessage.getContentRaw().length() > 1024) {
                    embed.addField("**Second part of deleted message**", oldMessage.getContentRaw().substring(1024), false);
                }
            }
            if (!oldMessage.getEmbeds().isEmpty()) {
                embed.addField("**Embeds**", "Found " + oldMessage.getEmbeds().size() + " embed(s). Not displayable for version " + VersionInfo.getLongVersion(), false);
            }
            if (!oldMessage.getAttachments().isEmpty()) {
                embed.setImage(oldMessage.getAttachments().get(0).getProxyUrl()); // most likely there is only one image so it will be displayed on the embed
                StringBuilder attachments = new StringBuilder();
                for (Message.Attachment attachment: oldMessage.getAttachments()) {
                    String url = attachment.getProxyUrl();
                    String downloadURL = "https://media.discordapp.net" + url.substring(url.indexOf("/attachments/"));
                    String filename = attachment.getFileName();
                    attachments.append("[").append(filename).append("](").append(downloadURL).append(")\n");
                }
                embed.addField("**Uploaded files**", attachments.toString(), false);
            }
            embed.setFooter("UserID: " + oldMessage.getAuthor().getId() + " | " + UniqueIDHandler.getNewUUID() + " | MessageDelete");
        } else {
            embed.setDescription("**Message deleted in <#" + event.getChannel().getId() + ">.**\n" +
                    "No further information could be fetched.");
            embed.setFooter("UserID: Not fetched | " + UniqueIDHandler.getNewUUID() + " | MessageDelete");
        }
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
