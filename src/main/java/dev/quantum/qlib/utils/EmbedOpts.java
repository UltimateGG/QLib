package dev.quantum.qlib.utils;

import dev.quantum.qlib.utils.placeholder.EmbedField;
import dev.quantum.qlib.utils.placeholder.FString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedOpts {
    private String title;
    private Color color;
    private String description;
    private String footer;
    private String thumbnail;

    public EmbedOpts() {}

    public EmbedOpts(String title, Color color, String description) {
        this(title, color, description, null, null);
    }

    public EmbedOpts(String title, Color color, String description, String thumbnail) {
        this(title, color, description, null, thumbnail);
    }

    public EmbedOpts(String title, Color color, String description, String footer, String thumbnail) {
        this.title = title;
        this.color = color;
        this.description = description;
        this.footer = footer;
        this.thumbnail = thumbnail;
    }

    public MessageEmbed createEmbed(FString... formatters) {
        String newTitle = this.title;
        String newDescription = this.description;
        String newThumbnail = this.thumbnail;
        String newFooter = this.footer;

        for (FString formatter : formatters) {
            newTitle = formatter.replace(EmbedField.TITLE, newTitle);
            newDescription = formatter.replace(EmbedField.DESCRIPTION, newDescription);
            newThumbnail = formatter.replace(EmbedField.THUMBNAIL, newThumbnail);
            newFooter = formatter.replace(EmbedField.FOOTER, newFooter);
        }

        return new EmbedBuilder()
                .setTitle(newTitle)
                .setColor(this.color)
                .setDescription(newDescription)
                .setFooter(newFooter)
                .setThumbnail(newThumbnail != null ? newThumbnail.startsWith("http://") ? newThumbnail : null : null)
                .build();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
