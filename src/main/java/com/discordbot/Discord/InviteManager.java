package com.discordbot.Discord;

import com.discordbot.Config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InviteManager {

    private static final Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());
    private static final @NotNull Map<@NotNull String, @NotNull Invite> invites = new LinkedHashMap<>();
    private static boolean loaded = false;

    public static Map<String, Invite> getInvites() {
        return invites;
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static void fetchInvites(JDA jda) {
        LOGGER.info("Fetch all invites on all selected guilds.");
        Object guildID = Config.get("handle_guild");
        jda.getGuildById(guildID.toString()).retrieveInvites().queue(success -> {
            if (success != null) {
                invites.clear();
                for (Invite i : success) {
                    invites.put(i.getCode(), i);
                }
                loaded = true;
            }
        }, failure -> {
        });

    }

    public static void setCurrentInvites(Map<String, Invite> newInvites) {
        invites.clear();
        invites.putAll(newInvites);
        loaded = true;
    }

    /**
     * Finds the used invite out of the old fetched ones and the given new ones.
     * Invite can be found by
     *
     * @param newInvites current fetched invites for guild
     * @return Invite obj if allocatable
     */

    public static Invite getUsedInviteByGuildID(List<Invite> newInvites) {
        if (!loaded  || newInvites == null) {
            // do not compare if bot did not fetch invites before
            return null;
        }
        Invite usedInvite = null;
        for (Invite possibleInvite : newInvites) {
            Invite availableInvite = invites.get(possibleInvite.getCode());
            if (availableInvite == null) {
                continue;
            }
            if (possibleInvite.getUses() != availableInvite.getUses()) { //
                usedInvite = possibleInvite;
                break;
            }
        }
        if (usedInvite == null) { // if not found yet, this means the invite expired
            Set<String> oldInvites = invites.keySet();
            Set<String> updatedInvites = newInvites.stream().map(Invite::getCode).collect(Collectors.toSet());
            oldInvites.removeAll(updatedInvites); // find  difference between old and new invite list, difference are expired invites
            if (oldInvites.size() == 1) { // found expired and used invite
                String i = oldInvites.toArray()[0].toString();
                usedInvite = invites.get(i);
                invites.remove(i);
            } else if (oldInvites.size() > 1) { // cannot allocate
                for (String i : oldInvites) {
                    invites.remove(i);
                }
                return null;
            }
        } else {
            invites.put(usedInvite.getCode(), usedInvite);
        }
        return usedInvite;
    }

}
