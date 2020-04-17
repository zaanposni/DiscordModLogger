package com.discordbot.Embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class SuccessLogEmbedTest {

    @Test
    public void testConstructor() {
        SuccessLogEmbed embed = new SuccessLogEmbed();
        Assert.assertNotNull(embed);
    }

    @Test
    public void buildEmbedShouldNotFail() {
        SuccessLogEmbed embed = new SuccessLogEmbed();
        MessageEmbed buildEmbed = embed.build();
        Assert.assertNotNull(buildEmbed);
    }
}