package com.discordbot.Listener;

import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.MessageCacher;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.FailureLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageDelete  extends ListenerAdapter {

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
            embed.setDescription("**Message deleted by " + oldMessage.getAuthor().getAsMention() + " in <#" + event.getChannel().getId() + ">.**");
            if (!oldMessage.getContentRaw().isEmpty()) {
                embed.addField("**Deleted message**", oldMessage.getContentRaw().substring(0, Math.min(Integer.parseInt(Config.get("cut_log_messages_to_characters", 1000).toString()), oldMessage.getContentRaw().length())), false);
            }
            if (oldMessage.getAttachments().size() == 1) {
                String url = oldMessage.getAttachments().get(0).getProxyUrl();
                int pos = url.indexOf("/attachments/");
                String downloadURL = "https://cdn.discordapp.com" + url.substring(pos);
                String filename = oldMessage.getAttachments().get(0).getFileName();
                embed.setImage(downloadURL);
                embed.addField("Uploaded file", "[" + filename + "](" + downloadURL + ")", false);
            }
            embed.setFooter("UserID: " + oldMessage.getAuthor().getId() + " | " + UniqueIDHandler.getNewUUID() + " | EventMessageDelete");
        } else {
            embed.setDescription("**Message deleted in <#" + event.getChannel().getId() + ">.**\n" +
                    "No further information could be fetched.");
            embed.setFooter("UserID: Not fetched | " + UniqueIDHandler.getNewUUID() + " | EventMessageDelete");
        }
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
