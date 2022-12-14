import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordIntegration extends ListenerAdapter {
    // Sets up and registers JDA listener
    public static void main(String[] args) throws Exception {
        JDA api = JDABuilder
                .createDefault("MTA1MDUzNjA0MTQxNDY2MDE1Nw.G49oVh.Gpj4b7S70tQEriVZw_LmHc-moq0fLF-ro5ADkA",
                        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new DiscordIntegration()).setActivity(Activity.playing("COS 126")).build();
    }

    // Server bot setup
    public void whenJoiningServer(GuildJoinEvent event) {
        TextChannel channel = event.getGuild().getTextChannels().get(0);
        channel.sendMessage("Thank you for inviting RancidRamen! Please type \"!setPrefix <prefix>\" to set custom prefixes" +
                "and type \"!help\" for commands and instructions.");
    }

    // check message
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // check user
        if (!event.getAuthor().isBot()) {
            // get ID
            String message = event.getMessage().getContentRaw();
            System.out.println(message);
            TextChannel currentChannel = event.getChannel().asTextChannel();
            String userId = event.getMember().getId();
            String serverId = event.getGuild().getId();

            // check if user exists; if not, add.
            MongoConnection mongoConnection = new MongoConnection(userId, serverId);

            // check prefix and workflow
            String prefix = mongoConnection.getPrefix();
            System.out.println(prefix);
            Functions workflow = new Functions();
            if (mongoConnection.getCurrentWorkflow().equals("vc")) {
                workflow = new FunctionsA();
            }
            if (message.equals(prefix + "help")) {
                currentChannel.sendMessage(workflow.help()).queue();
            }

        }
    }
}
