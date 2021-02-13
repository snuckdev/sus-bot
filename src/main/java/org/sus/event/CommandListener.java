package org.sus.event;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sus.Main;
import org.sus.command.manager.CommandManager;
import org.sus.util.Constants;

public class CommandListener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent e) {
        LOGGER.info("Client is ready: {}", e.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        if(!Main.getHikari().isClosed()) {
            Main.getHikari().close();
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent e) {
        User user = e.getAuthor();

        if(user.isBot() || e.isWebhookMessage()) {
            return;
        }

        String prefix = Constants.PREFIX;
        String raw = e.getMessage().getContentRaw();

        if(raw.startsWith(prefix)) {
            manager.handle(e);
        }
    }
}
