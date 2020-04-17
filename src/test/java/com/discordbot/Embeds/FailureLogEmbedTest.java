package com.discordbot.Embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class FailureLogEmbedTest {

    @Test
    public void initializingOfFailureLogEmbedShouldNotReturnNull() {
        FailureLogEmbed embed = new FailureLogEmbed();
        Assert.assertNotNull(embed);
    }

    @Test
    public void buildEmbedShouldNotFail() {
        FailureLogEmbed embed = new FailureLogEmbed();
        MessageEmbed buildEmbed = embed.build();
        Assert.assertNotNull(buildEmbed);
    }
}