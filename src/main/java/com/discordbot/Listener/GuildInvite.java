package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class GuildInvite extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        LOGGER.info(event.getInvite().getCode());
    }

    @Override
    public void onGuildInviteDelete(@Nonnull GuildInviteDeleteEvent event) {
        LOGGER.info(event.getCode());
    }
}
