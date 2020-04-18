package com.discordbot.Discord;

import com.discordbot.Config.Config;
import net.dv8tion.jda.api.entities.Message;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MessageCacher {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    private static final Map<String, Message> messages = new LinkedHashMap<>();
    private static final Integer cacheLimit = Integer.parseInt(Config.get("cache_messages", 100).toString());

    public static void addMessage(Message message) {
        while (messages.size() >= cacheLimit) {
            // remove messages exceeding cache limit
            messages.remove(messages.entrySet().iterator().next().getKey());
        }
        messages.put(message.getId(), message);
    }

    public static Message getMessageByID(String messageID) {
        return messages.get(messageID);
    }

    public static void removeMessageByID(String messageID)  {
        messages.remove(messageID);
    }
}
