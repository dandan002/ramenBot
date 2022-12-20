/******************************************************************************
 *
 * This program calls the methods in Functions.java based on user input from
 * Discord. It also sets up the bot in Discord and establishes a connection
 * to the MongoDB Atlas database.
 *
 *****************************************************************************/

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordIntegration extends ListenerAdapter {
    // Sets up and registers JDA listener
    public static void main(String[] args) throws Exception {
        JDA api = JDABuilder
                .createDefault("Redacted token",
                        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_WEBHOOKS,
                        GatewayIntent.MESSAGE_CONTENT).setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new DiscordIntegration()).
                setActivity(Activity.playing("COS 126")).build();
    }

    // Server bot setup
    public void whenJoiningServer(GuildJoinEvent event) {
        TextChannel channel = event.getGuild().getTextChannels().get(0);
        channel.sendMessage("Thank you for inviting RancidRamen! " +
                "Please type \"!setPrefix <prefix>\" to " +
                "set custom prefixes and type \"!help\" " +
                "for commands and instructions.");
    }

    // check message
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // check user
        if (!event.getAuthor().isBot()) {
            // get ID
            String message = event.getMessage().getContentRaw();
            TextChannel currentChannel = event.getChannel().asTextChannel();
            String userId = event.getMember().getId();
            String serverId = event.getGuild().getId();

            // check if user exists; if not, add.
            MongoConnection mongoConnection =
                    new MongoConnection(userId, serverId);
            // check prefix and workflow
            String prefix = mongoConnection.getPrefix();
            Functions workflow = new Functions(mongoConnection);

            // help
            if (message.equals(prefix + "help")) {
                currentChannel.sendMessage(workflow.help(prefix)).queue();
            }

            // work
            if (message.equals(prefix + "work")) {
                currentChannel.sendMessage(workflow.work()).queue();
                if (mongoConnection.getRamenCooked() > ((int)
                        (100 * mongoConnection.getUserLevel() * 0.75))) {
                    System.out.println("levelup confirmed");
                    currentChannel.sendMessage(workflow.levelUp()).queue();
                }
            }

            // check balance
            if (message.equals(prefix + "balance")) {
                currentChannel.sendMessage(workflow.balance()).queue();
            }

            // rob
            if (message.substring(0,4).equals(prefix + "rob")) {
                String target = message.substring(5);
                System.out.println(event.getGuild().getMembersByEffectiveName("kai", true).get(0));
                Member targetUser = event.getGuild().getMembersByEffectiveName(target, true).get(0);
                String username = targetUser.getId();
                MongoConnection target2 = new MongoConnection(username, serverId);
                currentChannel.sendMessage(workflow.rob(target2, target)).queue();
            }

            // display menu
            if (message.equals(prefix + "menu")) {
                currentChannel.sendMessage(workflow.menu(prefix)).queue();
            }
        }
    }
}
