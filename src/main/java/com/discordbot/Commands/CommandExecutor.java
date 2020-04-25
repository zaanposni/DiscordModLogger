package com.discordbot.Commands;

import com.discordbot.Discord.DiscordClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.logging.Logger;

public interface CommandExecutor {
    Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());
    void onExecute(Command cmd, JDA jda);
    String helpString();
}

