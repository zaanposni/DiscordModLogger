package com.discordbot.Embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class WarningLogEmbedTest {

    @Test
    public void testConstructor() {
        WarningLogEmbed embed = new WarningLogEmbed();
        Assert.assertNotNull(embed);
    }

    @Test
    public void buildEmbedShouldNotFail() {
        WarningLogEmbed embed = new WarningLogEmbed();
        MessageEmbed buildEmbed = embed.build();
        Assert.assertNotNull(buildEmbed);
    }
}