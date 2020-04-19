package com.discordbot.Discord;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

public class UniqueIDHandler {
    public static String getNewUUID(){
        return getNewUUID(null, null);
    }

    public static String getNewUUID(@Nullable String prefix, @Nullable String suffix) {
        StringBuilder builder = new StringBuilder();
        if (prefix != null)
                builder.append(prefix).append("_");

        DateTime now = new DateTime();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyMMdd");
        builder.append(Long.parseLong(dtf.print(now), 16));

        builder.append("_");
        builder.append(RandomStringUtils.randomAlphanumeric(4));

        if (suffix != null)
            builder.append("_").append(suffix);

        return builder.toString();
    }
}
