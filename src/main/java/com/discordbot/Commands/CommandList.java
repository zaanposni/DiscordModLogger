package com.discordbot.Commands;

import com.discordbot.Embeds.NeutralLogEmbed;
import com.discordbot.Meta.About;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

public class CommandList implements CommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) {
        EmbedBuilder embed = new NeutralLogEmbed();
        embed.setDescription("**List**\n```\n" + String.join("\n", About.getLoggedEvents()) + "```");
        cmd.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String helpString() {
        return "This gives information about all logged events on this guild";
    }
}
