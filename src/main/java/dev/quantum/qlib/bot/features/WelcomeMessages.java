package dev.quantum.qlib.bot.features;

import dev.quantum.qlib.bot.QBot;
import dev.quantum.qlib.utils.Colors;
import dev.quantum.qlib.utils.EmbedOpts;
import dev.quantum.qlib.utils.placeholder.GuildReplacer;
import dev.quantum.qlib.utils.placeholder.UserReplacer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.List;

/** Send a join/leave message in a specific channel on a member joining/leaving the server */
public class WelcomeMessages extends Feature {
    private boolean sendJoinMessage;
    private EmbedOpts joinEmbed = new EmbedOpts("Welcome", Colors.SUCCESS, "Welcome to the server, ${u_mention}!", "${g_icon}");
    private String joinChannel = "%SYSTEM_CHANNEL%";

    private boolean sendLeaveMessage;
    private EmbedOpts leaveEmbed = new EmbedOpts("Goodbye", Colors.RED, "${u_mention} (${u_tag}) has left the server.", "${g_icon}");
    private String leaveChannel = "%SYSTEM_CHANNEL%";

    public WelcomeMessages() {
        this(true, true);
    }

    public WelcomeMessages(boolean joinMessages, boolean leaveMessages) {
        this.sendJoinMessage = joinMessages;
        this.sendLeaveMessage = leaveMessages;
    }

    @SubscribeEvent
    public void onGuildJoin(GuildMemberJoinEvent e) {
        if (!this.sendJoinMessage) return;
        TextChannel channel = getChannel(e.getGuild(), this.joinChannel);
        if (channel == null) {
            QBot.LOGGER.warn("Could not find channel for join message: " + this.joinChannel);
            return;
        }

        channel.sendMessageEmbeds(joinEmbed.createEmbed(new UserReplacer(e.getUser()), new GuildReplacer(e.getGuild()))).queue();
    }

    @SubscribeEvent
    public void onGuildLeave(GuildMemberRemoveEvent e) {
        if (!this.sendLeaveMessage) return;
        TextChannel channel = getChannel(e.getGuild(), this.leaveChannel);
        if (channel == null) {
            QBot.LOGGER.warn("Could not find channel for leave message: " + this.leaveChannel);
            return;
        }

        channel.sendMessageEmbeds(leaveEmbed.createEmbed(new UserReplacer(e.getUser()), new GuildReplacer(e.getGuild()))).queue();
    }

    private TextChannel getChannel(Guild g, String channel) {
        if (channel.equals("%SYSTEM_CHANNEL%")) return g.getSystemChannel();

        // Try by name first
        List<TextChannel> channels = g.getTextChannelsByName(channel, true);
        if (channels.size() > 1) QBot.LOGGER.warn("[WelcomeMessages] Found multiple channels with name: " + channel);
        if (channels.size() > 0) return channels.get(0);

        try { // Try by ID
            return g.getTextChannelById(channel);
        } catch (Exception ignored) {}

        return null;
    }

    // Options builder
    public boolean sendJoinMessages() {
        return sendJoinMessage;
    }

    public WelcomeMessages setSendJoinMessage(boolean sendJoinMessage) {
        this.sendJoinMessage = sendJoinMessage;
        return this;
    }

    /**
     * Valid placeholders are:
     * ${u_mention} - An @Mention of the user
     * ${u_tag} - The user's tag (username#discriminator)
     * ${u_name} - The user's username
     * ${u_id} - The user's discord ID
     * ${g_name} - The guild's name
     * ${g_icon} - The guild's icon image
     * ${g_id} - The guild's ID
     */
    public WelcomeMessages setJoinFormat(EmbedOpts embed) {
        this.joinEmbed = embed;
        return this;
    }

    public String getJoinChannel() {
        return joinChannel;
    }

    public WelcomeMessages setJoinChannel(String joinChannel) {
        this.joinChannel = joinChannel;
        return this;
    }

    public boolean sendLeaveMessage() {
        return sendLeaveMessage;
    }

    public WelcomeMessages setSendLeaveMessage(boolean sendLeaveMessage) {
        this.sendLeaveMessage = sendLeaveMessage;
        return this;
    }

    /**
     * Valid placeholders are:
     * ${u_mention} - An @Mention of the user
     * ${u_tag} - The user's tag (username#discriminator)
     * ${u_name} - The user's username
     * ${u_id} - The user's discord ID
     * ${g_name} - The guild's name
     * ${g_icon} - The guild's icon image
     * ${g_id} - The guild's ID
     */
    public WelcomeMessages setLeaveFormat(EmbedOpts embed) {
        this.leaveEmbed = embed;
        return this;
    }

    public String getLeaveChannel() {
        return leaveChannel;
    }

    public WelcomeMessages setLeaveChannel(String leaveChannel) {
        this.leaveChannel = leaveChannel;
        return this;
    }
}
