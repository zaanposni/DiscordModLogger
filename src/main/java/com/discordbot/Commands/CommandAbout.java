package com.discordbot.Commands;

import com.discordbot.Embeds.NeutralLogEmbed;
import com.discordbot.Meta.About;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

public class CommandAbout implements CommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) {
        LOGGER.info("test");
        EmbedBuilder embed = new NeutralLogEmbed();
        embed.setDescription("**About**\n" + About.getInfo());
        cmd.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String helpString() {
        return "This gives information about the bot version and other stuff.";
    }
}
