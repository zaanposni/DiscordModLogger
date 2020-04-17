package com.discordbot.Listener;

import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.MessageCacher;
import com.discordbot.Discord.Sender;
import com.discordbot.Embeds.FailureLogEmbed;
import com.discordbot.Embeds.WarningLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.junit.internal.runners.statements.Fail;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageDelete  extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
        if (Config.getArray("exclude_channels").contains(event.getChannel().getId())) {
            // do not log these channels
            return;
        }

        if (event.getChannelType() != ChannelType.TEXT) {
            // do not log DMs
            return;
        }

        LOGGER.info("Received Message deletion.");

        // Get old and delete from cache
        Message oldMessage = MessageCacher.getMessageByID(event.getMessageId());
        MessageCacher.removeMessageByID(event.getMessageId());

        EmbedBuilder embed = new FailureLogEmbed();
        if (oldMessage != null) {
            LOGGER.info("Found old version of message in cache.");
            embed.setAuthor(oldMessage.getAuthor().getName(), oldMessage.getJumpUrl(), oldMessage.getAuthor().getAvatarUrl());
            embed.setDescription("**Message deleted by " + oldMessage.getAuthor().getAsMention() + " in <#" + event.getChannel().getId() + ">.**");
            embed.addField("**Deleted message**", oldMessage.getContentRaw().substring(0, Math.min(1000, oldMessage.getContentRaw().length())), false);
            embed.setFooter("AuthorID: " + oldMessage.getAuthor().getId() + " | MessageID: " + oldMessage.getAuthor().getId());
        } else {
            embed.setDescription("**Message deleted in <#" + event.getChannel().getId() + ">.**");
            embed.setFooter("MessageID: " + event.getMessageId() + " | ChannelID: " + event.getChannel().getId());
        }
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
