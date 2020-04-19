package com.discordbot.Commands;

import com.discordbot.Config.Config;
import com.discordbot.Exceptions.CommandCreationFailedException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class Command {
    private User author;
    private String prefix;
    private String invoke;
    private List<String> args;
    private MessageChannel channel;
    private Guild guild;
    private Message message;

    public Command() { }

    public Command(MessageReceivedEvent event) throws IndexOutOfBoundsException, CommandCreationFailedException {
        this.author = event.getAuthor();
        this.channel = event.getChannel();
        this.guild = event.getGuild();
        this.message =event.getMessage();

        String messageContent = event.getMessage().getContentRaw();

        if (messageContent.isEmpty())
            return;

        if (!messageContent.startsWith(Config.get("command_prefix", "_").toString()))
            return;

        this.prefix = Config.get("command_prefix", "_").toString();
        this.args = Arrays.asList(messageContent.split(" "));
        this.invoke = args.get(0).substring(1);
        if (invoke.isEmpty() || prefix == null)
            throw new CommandCreationFailedException("Failed to create Command. Most likely due to an invalid invoke.");
    }

    public User getAuthor() {
        return author;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getInvoke() {
        return invoke;
    }

    public List<String> getArgs() {
        return args;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Guild getGuild() {
        return guild;
    }

    public Message getMessage() {
        return message;
    }
}
