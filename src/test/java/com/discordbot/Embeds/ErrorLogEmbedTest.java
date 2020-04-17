package com.discordbot.Embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class ErrorLogEmbedTest {

    @Test
    public void initializingOfErrorLogEmbedShouldNotReturnNull() {
        ErrorLogEmbed embed = new ErrorLogEmbed();
        Assert.assertNotNull(embed);
    }

    @Test
    public void buildEmbedShouldNotFail() {
        ErrorLogEmbed embed = new ErrorLogEmbed();
        MessageEmbed buildEmbed = embed.build();
        Assert.assertNotNull(buildEmbed);
    }
}