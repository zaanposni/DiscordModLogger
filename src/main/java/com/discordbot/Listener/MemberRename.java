package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.Sender;
import com.discordbot.Embeds.SuccessLogEmbed;
import com.discordbot.Embeds.WarningLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MemberRename extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        LOGGER.info("Member " + event.getOldNickname() + " renamed to " + event.getNewNickname());
        EmbedBuilder embed = new WarningLogEmbed();
        embed.setAuthor(event.getUser().getName(), event.getUser().getDefaultAvatarUrl(), event.getUser().getAvatarUrl());
        embed.setDescription(event.getUser().getAsMention() + "** changed his nickname.**");
        if (event.getOldNickname() != null) {
            embed.addField("**Old name**", event.getOldNickname(), true);
        } else {
            embed.addField("**Old name**", event.getUser().getName(), true);
        }
        if (event.getNewNickname() != null) {
            embed.addField("**New name**", event.getNewNickname(), true);
        } else {
            embed.addField("**New name**", event.getUser().getName(), true);
        }
        embed.setFooter("UserID: " + event.getUser().getId());
        Sender.sendToAllLogChannels(event, embed.build());
    }

    @Override
    public void onUserUpdateName(@Nonnull UserUpdateNameEvent event) {
        LOGGER.info("Member " + event.getOldName() + " renamed to " + event.getNewName());
        EmbedBuilder embed = new WarningLogEmbed();
        embed.setAuthor(event.getUser().getName(), event.getUser().getDefaultAvatarUrl(), event.getUser().getAvatarUrl());
        embed.setDescription(event.getUser().getAsMention() + "** changed his discord name.**");
        embed.addField("**Old name**", event.getOldName(), true);
        embed.addField("**New name**", event.getNewName(), true);
        embed.setFooter("UserID: " + event.getUser().getId());
        Sender.sendToAllLogChannels(event, embed.build());
    }
}
