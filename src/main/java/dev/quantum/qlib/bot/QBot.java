package dev.quantum.qlib.bot;

import com.google.common.base.Throwables;
import dev.quantum.qlib.bot.features.Feature;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;

public class QBot {
    private final String name;
    private static JDA bot;
    public static Logger LOGGER = LoggerFactory.getLogger(QBot.class);
    private final ArrayList<Feature> features = new ArrayList<>();

    public QBot(String token) {
        this("Quantum", token, new BotOptions());
    }

    public QBot(String name, String token) {
        this(name, token, new BotOptions());
    }

    public QBot(String token, Feature... features) {
        this("Quantum", token, new BotOptions(), features);
    }

    public QBot(String name, String token, Feature... features) {
        this(name, token, new BotOptions(), features);
    }

    public QBot(String name, String token, BotOptions opts, Feature... featuresIn) {
        this.name = name;
        LOGGER = LoggerFactory.getLogger(name);

        try {
            LOGGER.info("Creating a new QuantumBot...");
            JDABuilder builder = (opts.intents != null)
                    ? JDABuilder.createDefault(token, opts.intents)
                    : JDABuilder.createDefault(token);

            bot = builder.setEventManager(opts.eventManager)
                    .addEventListeners(this, opts.eventListeners)
                    .setStatus(opts.status)
                    .setActivity(opts.activity)
                    .build()
                    .awaitReady();
        } catch (LoginException e) {
            LOGGER.error("Failed to login to Discord", e);
            Throwables.propagateIfPossible(e, RuntimeException.class);
        } catch (InterruptedException e) {
            LOGGER.error("Failed to setup bot", e);
            Throwables.propagateIfPossible(e, RuntimeException.class);
        }

        LOGGER.info("Bot successfully logged in");
        for (Guild guild : bot.getGuilds()) LOGGER.info("Found Guild: {}", guild.getName());

        Collections.addAll(this.features, featuresIn);
        for (Feature feature : features) {
            bot.addEventListener(feature);
            feature.setup(this);
        }

        LOGGER.info("Successfully created bot with {} feature(s)", features.size());
    }

    @SubscribeEvent
    public void onReady(ReadyEvent event) {
        LOGGER.info("Bot ready");
    }

    public String getName() {
        return name;
    }

    public static JDA getBot() {
        return bot;
    }

    public JDA bot() {
        return bot;
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }
}
