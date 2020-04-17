package com.discordbot;

import com.discordbot.Config.Config;
import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.DiscordConnectionHandling;
import com.discordbot.Listener.MemberGuildJoin;
import com.discordbot.Listener.MessageReceived;
import com.discordbot.Listener.MessageUpdate;
import com.discordbot.Meta.About;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DiscordLogger {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    public static void setDebugLevel(Level newLvl) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(newLvl);
        for (Handler h : rootLogger.getHandlers()) {
            System.out.println(h.toString());
            h.setLevel(newLvl);
        }
    }

    public static void main(String[] args) {
        setDebugLevel(Level.INFO);
        LOGGER.info("Starting up.");
        LOGGER.info(About.getInfo());

        LOGGER.fine("Initialize config.");
        if (Config.generateConfig() && Config.loadConfig()) {
            LOGGER.info("Successfully created config.json or already exist.");
        } else {
            LOGGER.severe("Initializing config failed");
            System.exit(1);
        }

        DiscordClient client = new DiscordClient();
        if (!DiscordConnectionHandling.isConnected()) {
            LOGGER.severe("Could not connect to Discord API.");
            System.exit(1);
        }

    }
}
