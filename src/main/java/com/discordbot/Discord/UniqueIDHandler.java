package com.discordbot.Discord;

import org.apache.commons.lang.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

public class UniqueIDHandler {
    public static @NotNull String getNewUUID(){
        return getNewUUID(null);
    }

    public static @NotNull String getNewUUID(@Nullable String suffix) {
        StringBuilder builder = new StringBuilder();
        builder.append("#");

        DateTime now = new DateTime();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyMMdd");
        builder.append(Long.parseLong(dtf.print(now), 16));

        builder.append("_");
        builder.append(RandomStringUtils.randomAlphanumeric(6));

        if (suffix != null)
            builder.append("_").append(suffix);

        return builder.toString();
    }
}
