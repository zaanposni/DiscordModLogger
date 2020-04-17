package com.discordbot.Embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class NeutralLogEmbedTest {

    @Test
    public void initializingOfNeutralLogEmbedShouldNotReturnNull() {
        NeutralLogEmbed embed = new NeutralLogEmbed();
        Assert.assertNotNull(embed);
    }

    @Test
    public void buildEmbedShouldNotFail() {
        NeutralLogEmbed embed = new NeutralLogEmbed();
        MessageEmbed buildEmbed = embed.build();
        Assert.assertNotNull(buildEmbed);
    }
}