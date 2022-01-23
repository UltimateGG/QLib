import dev.quantum.qlib.bot.QBot;
import dev.quantum.qlib.bot.features.AutoRole;
import dev.quantum.qlib.bot.features.WelcomeMessages;

public class Test {
    public static void main(String[] args) {
        QBot bot = new QBot(
                "QuantumBot",
                System.getProperty("quantum.token"),
                new WelcomeMessages(true, true),
                new AutoRole("Member")
        );
    }
}
