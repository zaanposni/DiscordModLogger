package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.FailureLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MemberGuildBan extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildBan(@Nonnull GuildBanEvent event) {
        LOGGER.info("Member " + event.getUser().getName() + " banned from guild " + event.getGuild().getName());
        EmbedBuilder embed = new FailureLogEmbed();
        User user = event.getUser();
        embed.setAuthor("Member banned", user.getAvatarUrl(), user.getAvatarUrl());
        embed.setDescription(user.getAsMention() + " | " + user.getAsTag());
        embed.setFooter("UserID: " + user.getId() + " | " + UniqueIDHandler.getNewUUID() + " | MemberBan");
        event.getGuild().retrieveBan(event.getUser()).queue(success -> {
            if (success.getReason() != null) {
                embed.addField("**Reason**", success.getReason(), false);
            }
            Sender.sendToAllLogChannels(event, embed.build());
        }, failure -> {
            LOGGER.info("Failed to fetch ban reason.");
            embed.addField("**Reason**", "No reason given", false);
            Sender.sendToAllLogChannels(event, embed.build());
        });
    }
}
