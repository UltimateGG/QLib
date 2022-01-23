package dev.quantum.qlib.utils.placeholder;

import net.dv8tion.jda.api.entities.User;

public class UserReplacer extends FString {
    private final User user;

    public UserReplacer(User user) {
        this.user = user;
    }

    @Override
    public String replace(EmbedField field, String str) {
        if (user == null || str == null) return str;
        return str.replaceAll("\\$\\{u_mention\\}", user.getAsMention())
                .replaceAll("\\$\\{u_tag\\}", user.getAsTag())
                .replaceAll("\\$\\{u_name\\}", user.getName())
                .replaceAll("\\$\\{u_id\\}", user.getId());
    }
}
