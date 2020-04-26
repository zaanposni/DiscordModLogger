package com.discordbot.Meta;

public class VersionInfo {
    public static String getVersion() {
        return version;
    }

    public static String getLongVersion() {
        if (subVersion.isEmpty()) {
            return "v." + version;
        }
        return "v." + version + "-" + subVersion;
    }

    private static final String version = "1.2.3";
    private static final String subVersion = "SNAPSHOT";
}
