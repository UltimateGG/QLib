package dev.quantum.qlib.bot.features;

import dev.quantum.qlib.bot.QBot;

public abstract class Feature {
    protected QBot bot;

    public void setup(QBot bot) {
        this.bot = bot;
    }
}
