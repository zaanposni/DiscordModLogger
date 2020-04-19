package com.discordbot.Commands;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Exceptions.CommandCreationFailedException;
import com.discordbot.Exceptions.CommandExecuteException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.logging.Logger;

public class CommandHandler {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());
    // register commands here
    private static final HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>() {{
        put("help", new CommandHelp());
        put("about", new CommandAbout());
        put("list", new CommandList());
    }};

    public static HashMap<String, CommandExecutor> getCommands() { return commands; }

    public static void handle(MessageReceivedEvent event) throws CommandCreationFailedException, CommandExecuteException {
        Command cmd;
        try {
            LOGGER.info("Try to parse received message to command");
            cmd = new Command(event);
        }
        catch (IndexOutOfBoundsException ex) {
            throw new CommandCreationFailedException("Failed to create Command. Most likely due to an invalid invoke.");
        }
        LOGGER.info("Invoke found: " + cmd.getInvoke());
        if (commands.containsKey(cmd.getInvoke())) {
            CommandExecutor ex = commands.get(cmd.getInvoke());
            try {
                LOGGER.info(cmd.getAuthor().getAsTag() + " issued " + cmd.getInvoke());
                LOGGER.info("Executing command " + cmd.getInvoke());
                ex.onExecute(cmd, event.getJDA());
            } catch (Exception e) {
                throw new CommandExecuteException("Failed to execute command " + cmd.getInvoke(), e);
            }
        } else {
            LOGGER.info("No command found for this invoke");
        }
    }
}

