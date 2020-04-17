package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.InviteManager;
import com.discordbot.Discord.Sender;
import com.discordbot.Embeds.FailureLogEmbed;
import com.discordbot.Embeds.SuccessLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class GuildInvite extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        LOGGER.info("Invite " + event.getUrl() + " created for guild " + event.getGuild().getName());
        EmbedBuilder embed = new SuccessLogEmbed();
        Invite invite = event.getInvite();
        User user = invite.getInviter();
        embed.setAuthor(user.getName(), user.getAvatarUrl(), user.getAvatarUrl());
        embed.setDescription(user.getAsMention() + "** created an invite.**\n" +
                "Invite: [" + invite.getUrl() + "](" + invite.getUrl() + ")\n" +
                "Leading to channel: <#" + invite.getChannel().getId() + ">.");
        embed.setFooter("UserID: " + user.getId() + " | ChannelID: " + event.getChannel().getId());
        Sender.sendToAllLogChannels(event, embed.build());
        InviteManager.fetchInvites(event.getJDA());
    }

    @Override
    public void onGuildInviteDelete(@Nonnull GuildInviteDeleteEvent event) {
        LOGGER.info("Invite " + event.getUrl() + " deleted for guild " + event.getGuild().getName());
        EmbedBuilder embed = new FailureLogEmbed();
        embed.setDescription("**Invite deleted.**\n" +
                "Invite: [" + event.getUrl() + "](" + event.getUrl() + ")\n" +
                "Leading to channel: <#" + event.getChannel().getId() + ">.");
        embed.setFooter("ChannelID: " + event.getChannel().getId());
        Sender.sendToAllLogChannels(event, embed.build());
        InviteManager.fetchInvites(event.getJDA());
    }
}
