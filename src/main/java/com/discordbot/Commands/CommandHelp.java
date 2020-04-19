package com.discordbot.Commands;

import com.discordbot.Config.Config;
import com.discordbot.Embeds.FailureLogEmbed;
import com.discordbot.Embeds.NeutralLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

public class CommandHelp implements CommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) {
        String askedFor = "help";
        EmbedBuilder embed = new NeutralLogEmbed();
        try {
            askedFor = cmd.getArgs().get(1);
        } catch(IndexOutOfBoundsException ignored) { }
        try {
            CommandExecutor c = CommandHandler.getCommands().get(askedFor);
            String helpString = c.helpString();
            if (helpString == null) {
                EmbedBuilder failureEmbed = new FailureLogEmbed();
                failureEmbed.setDescription("**Help**\n" +
                        "No description found for this command");
                cmd.getChannel().sendMessage(failureEmbed.build()).queue();
                return;
            }
            embed.setDescription("**Help - " + askedFor.trim() + "**\n" + helpString);


        } catch(NullPointerException ex) {
            embed.setDescription("**Help**\n" + this.helpString());
        }
        cmd.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String helpString() {
        return "Usage: " + Config.get("command_prefix", "_") + "help <command>\n" +
                "Provides a help string for a command\n";
    }
}
