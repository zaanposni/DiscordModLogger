package com.discordbot.Discord;

import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class DiscordConnectionHandling extends ListenerAdapter {

    private static boolean connected;
    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    public static boolean isConnected() {
        return connected;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("Bot connected to Discord API.");
        connected = true;
        InviteManager.fetchInvites(event.getJDA());
    }

    @Override
    public void onReconnect(@Nonnull ReconnectedEvent event) {
        LOGGER.info("Bot reconnected to Discord API.");
        connected = true;
    }

    @Override
    public void onResume(@Nonnull ResumedEvent event) {
        LOGGER.info("Bot reconnected to Discord API.");
        connected = true;
    }

    @Override
    public void onDisconnect(@Nonnull DisconnectEvent event) {
        LOGGER.severe("Bot disconnected to Discord API.");
        connected = false;
    }
}
