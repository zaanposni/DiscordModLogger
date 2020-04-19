package com.discordbot.Listener;

import com.discordbot.Discord.DiscordClient;
import com.discordbot.Discord.InviteManager;
import com.discordbot.Discord.Sender;
import com.discordbot.Discord.UniqueIDHandler;
import com.discordbot.Embeds.SuccessLogEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class MemberGuildJoin extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        LOGGER.info("Member " + event.getUser().getName() + " joined guild " + event.getGuild().getName());
        EmbedBuilder embed = new SuccessLogEmbed();
        User user = event.getUser();
        embed.setAuthor("Member joined", user.getAvatarUrl(), user.getAvatarUrl());
        embed.setDescription(user.getAsMention() + " | " + user.getAsTag());
        embed.setFooter("userID: " + user.getId() + " | " + UniqueIDHandler.getNewUUID() + " | EventMemberJoin");

        LOGGER.info("Fetch which invite the member used.");
        event.getJDA().getGuildById(event.getGuild().getId()).retrieveInvites().queue(success -> {
            Invite usedInvite = InviteManager.getUsedInviteByGuildID(success);
            if (usedInvite == null) {
                LOGGER.info("Could not fetch used invite.");
            } else {
                User inviter = usedInvite.getInviter();
                if (inviter != null) {
                    embed.addField("Invite link", "Used invite: [" + usedInvite.getUrl() + "](" + usedInvite.getUrl() + ")\n" +
                            "Issued by: " + inviter.getAsMention() + " | " + inviter.getAsTag() + "\n" +
                            "Issued at: " + usedInvite.getTimeCreated().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), false);
                } else {
                    embed.addField("Invite link", "Used invite: " + usedInvite.getCode(), false);
                }
            }
            Sender.sendToAllLogChannels(event, embed.build());
        }, failure -> Sender.sendToAllLogChannels(event, embed.build()));


    }
}
