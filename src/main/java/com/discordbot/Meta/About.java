package com.discordbot.Meta;

import java.util.ArrayList;
import java.util.List;

public class About {
    public static String getInfo() {
        return info;
    }
    public static List<String> getLoggedEvents() { return loggedEvents; }


    private static final String info = "Discord Logger by zaanposni for easy moderation and insights of Discord guilds.\n" +
            "Version: " + VersionInfo.getLongVersion() + "\n" +
            "GitHub: https://github.com/zaanposni/DiscordModLogger/";

    private static final List<String> loggedEvents = new ArrayList<String>() {{
        add("InviteCreate");
        add("InviteDelete");
        add("MemberBan");
        add("MemberUnban");
        add("MemberJoin");
        add("MemberLeave");
        add("MemberRenick");
        add("MemberRename");
        add("MessageDelete");
        add("MessageReceive");
        add("MessageUpdate");
    }};
}
