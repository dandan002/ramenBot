import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordIntegration extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        JDA api = JDABuilder
                .createDefault("MTA1MDUzNjA0MTQxNDY2MDE1Nw.G49oVh.Gpj4b7S70tQEriVZw_LmHc-moq0fLF-ro5ADkA")
                .addEventListeners(new DiscordIntegration()).setActivity(Activity.playing("COS 126")).build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("A message sent.");
    }
}
