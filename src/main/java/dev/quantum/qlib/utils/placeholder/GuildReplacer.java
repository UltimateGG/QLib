package dev.quantum.qlib.utils.placeholder;

import net.dv8tion.jda.api.entities.Guild;

public class GuildReplacer extends FString {
    private final Guild guild;

    public GuildReplacer(Guild guild) {
        this.guild = guild;
    }

    @Override
    public String replace(EmbedField field, String str) {
        if (guild == null || str == null) return str;
        return str.replaceAll("\\$\\{g_name\\}", guild.getName())
                .replaceAll("\\$\\{g_icon\\}", guild.getIconUrl() == null ? "" : guild.getIconUrl())
                .replaceAll("\\$\\{g_id\\}", guild.getId());
    }
}
