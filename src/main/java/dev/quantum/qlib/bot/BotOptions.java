package dev.quantum.qlib.bot;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

public class BotOptions {
    public String prefix;
    public IEventManager eventManager;
    public OnlineStatus status;
    public Activity activity;
    public Object[] eventListeners;
    public Collection<GatewayIntent> intents;

    /**
     * Default options.
     * Prefix: "."
     * EventManager: AnnotatedEventManager (@SubscribeEvent)
     * Status: OnlineStatus.DO_NOT_DISTURB
     * Activity: "Watching: for .help"
     */
    public BotOptions() {
        this(".", new AnnotatedEventManager(), OnlineStatus.DO_NOT_DISTURB, Activity.watching("for .help"));
    }

    /** Default options with custom prefix */
    public BotOptions(String prefix) {
        this(prefix, new AnnotatedEventManager(), OnlineStatus.DO_NOT_DISTURB, Activity.watching("for " + prefix + "help"));
    }

    /** Default options with custom prefix & status */
    public BotOptions(String prefix, OnlineStatus status, Activity activity) {
        this(prefix, new AnnotatedEventManager(), status, activity);
    }

    public BotOptions(String prefix, IEventManager eventManager, OnlineStatus status, Activity activity, Object... eventListeners) {
        this.prefix = prefix;
        this.eventManager = eventManager;
        this.status = status;
        this.activity = activity;
        this.eventListeners = eventListeners != null ? eventListeners : new Object[0];
        this.intents = EnumSet.allOf(GatewayIntent.class); // Default all intents because we don't want to constantly change this
    }

    public BotOptions setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public BotOptions setEventListeners(Object... eventListeners) {
        this.eventListeners = eventListeners;
        return this;
    }

    public BotOptions addEventListener(Object eventListener) {
        Object[] newListeners = new Object[this.eventListeners.length + 1];
        System.arraycopy(this.eventListeners, 0, newListeners, 0, this.eventListeners.length);
        newListeners[this.eventListeners.length] = eventListener;
        this.eventListeners = newListeners;
        return this;
    }

    public BotOptions setIntents(Collection<GatewayIntent> intents) {
        this.intents = intents;
        return this;
    }

    public BotOptions setIntents(GatewayIntent... intents) {
        this.intents = Arrays.asList(intents);
        return this;
    }
}
