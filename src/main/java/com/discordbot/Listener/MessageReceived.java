package com.discordbot.Listener;

import com.discordbot.Commands.CommandHandler;
import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.MessageCacher;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.NeutralLogEmbed;
import com.discordbot.Exceptions.CommandCreationFailedException;
import com.discordbot.Exceptions.CommandExecuteException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageReceived extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return; // do not log self

        if (Config.getArray("bot_command_channels").contains(event.getChannel().getId())) {
            try {
                CommandHandler.handle(event);
            } catch (CommandCreationFailedException ignored) {
            } catch (CommandExecuteException e) {
                e.printStackTrace();
            }
        }

        if (Config.getArray("exclude_channels_from_log").contains(event.getChannel().getId()))
            return; // do not log these channels

        if (event.getMessage().getType() != MessageType.DEFAULT || event.getChannelType() != ChannelType.TEXT)
            return; // do not log system messages and DMs

        LOGGER.info("Received Message.");

        // Cache
        MessageCacher.addMessage(event.getMessage());

        if (!Boolean.parseBoolean(Config.get("log_received_message", "true").toString())) {
            return;
        }

        // Log
        EmbedBuilder embed = new NeutralLogEmbed();
        Message message = event.getMessage();
        User author = event.getAuthor();
        embed.setAuthor(author.getName(),event.getMessage().getJumpUrl(), author.getAvatarUrl());
        embed.setDescription("**Message sent by " + author.getAsMention() + " in " + "<#" + event.getChannel().getId() + ">.** [Jump](" + message.getJumpUrl() + ")");
        if (!message.getContentRaw().isEmpty()) {
            embed.addField("**New message**", message.getContentRaw().substring(0, Math.min(1024, message.getContentRaw().length())), false);
            if (message.getContentRaw().length() > 1024) {
                embed.addField("**Second part of message**", message.getContentRaw().substring(1024), false);
            }
        }
        if (message.getAttachments().size() == 1) {
            String url = message.getAttachments().get(0).getProxyUrl();
            int pos = url.indexOf("/attachments/");
            String downloadURL = "https://cdn.discordapp.com" + url.substring(pos);
            String filename = message.getAttachments().get(0).getFileName();
            embed.setImage(downloadURL);
            embed.addField("Uploaded file", "[" + filename + "](" + downloadURL + ")", false);
        }
        embed.setFooter("UserID: " + author.getId() + " | " + UniqueIDHandler.getNewUUID() + " | MessageReceive");
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
