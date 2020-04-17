package com.discordbot.Meta;

import org.junit.Assert;
import org.junit.Test;

public class VersionInfoTest {
    @Test
    public void getVersion() {
        String receive = VersionInfo.getVersion();
        Assert.assertNotNull(receive);
    }

    @Test
    public void getLongVersion() {
        String receive = VersionInfo.getLongVersion();
        Assert.assertNotNull(receive);
    }
}