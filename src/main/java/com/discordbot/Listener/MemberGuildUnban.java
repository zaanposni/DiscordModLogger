package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.Sender;
import com.discordbot.Embeds.FailureLogEmbed;
import com.discordbot.Embeds.SuccessLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MemberGuildUnban extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildUnban(@Nonnull GuildUnbanEvent event) {
        LOGGER.info("Member " + event.getUser().getName() + " unbanned from guild " + event.getGuild().getName());
        EmbedBuilder embed = new SuccessLogEmbed();
        User user = event.getUser();
        embed.setAuthor("Member unbanned", user.getAvatarUrl(), user.getAvatarUrl());
        embed.setDescription(user.getAsMention() + " | " + user.getAsTag());
        embed.setFooter("UserID: " + user.getId());
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
