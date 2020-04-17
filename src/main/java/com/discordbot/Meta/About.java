package com.discordbot.Meta;

public class About {
    public static String getInfo() {
        return info;
    }

    private static final String info = "Discord Logger by zaanposni for easy moderation and insights of Discord guilds.\n" +
            "Version: " + VersionInfo.getLongVersion() + "\n" +
            "GitHub: https://github.com/zaanposni/DiscordModLogger/";
}
