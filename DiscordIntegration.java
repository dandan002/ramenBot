import java.io.IOException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordIntegration extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDABuilder api = JDABuilder
        .createDefault("MTA1MDUzNjA0MTQxNDY2MDE1Nw.G49oVh.Gpj4b7S70tQEriVZw_LmHc-moq0fLF-ro5ADkA")
        .setActivity(Activity.playing("COS 126"));
        api.build;
    }
}
