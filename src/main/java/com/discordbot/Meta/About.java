package com.discordbot.Meta;

public class About {
    public static String getInfo() {
        return info;
    }

    private static final String info = "Discord Logger by zaanposni for easy moderation and insights of Discord guilds." +
            "Version: " + VersionInfo.getLongVersion() +
            "GitHub: https://github.com/zaanposni/DiscordLogger";
}
