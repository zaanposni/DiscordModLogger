package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.FailureLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MemberGuildLeave extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        LOGGER.info("Member " + event.getUser().getName() + " left guild " + event.getGuild().getName());
        EmbedBuilder embed = new FailureLogEmbed();
        User user = event.getUser();
        embed.setAuthor("Member left", user.getAvatarUrl(), user.getAvatarUrl());
        embed.setDescription(user.getAsMention() + " | " + user.getAsTag());
        embed.setFooter("UserID: " + user.getId() + " | " + UniqueIDHandler.getNewUUID() + " | EventMemberLeave");
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
