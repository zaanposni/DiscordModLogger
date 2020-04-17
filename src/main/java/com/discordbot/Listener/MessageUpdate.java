package com.discordbot.Listener;

import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.MessageCacher;
import com.discordbot.Discord.Sender;
import com.discordbot.Embeds.WarningLogEmbed;
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
        if (Config.getArray("exclude_channels").contains(event.getChannel().getId())) {
            // do not log these channels
            return;
        }

        if (event.getAuthor() == event.getJDA().getSelfUser()) {
            // do not log self
            return;
        }

        if (event.getMessage().getType() != MessageType.DEFAULT || event.getChannelType() != ChannelType.TEXT) {
            // do not log system messages and DMs
            return;
        }

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
        embed.setDescription("**Message edited by " + author.getAsMention() + " in " + "<#" + event.getChannel().getId() + ">.**");
        if (oldMessage != null) {
            embed.addField("**Old message**", oldMessage.getContentRaw().substring(0, Math.min(1000, oldMessage.getContentRaw().length())), false);
        }
        embed.addField("**Updated message**", message.getContentRaw().substring(0, Math.min(1000, message.getContentRaw().length())), false);
        embed.addField("**Jump to message**", "[Click here.](" + message.getJumpUrl() + ")", false);
        embed.setFooter("AuthorID: " + author.getId() + " | MessageID: " + message.getId());
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
