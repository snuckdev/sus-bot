package org.sus.command.manager;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.command.command.administration.*;
import org.sus.command.command.fun.ClearFortniteCommand;
import org.sus.command.command.fun.FortniteCommand;
import org.sus.command.command.fun.SetFortniteCommand;
import org.sus.command.command.help.HelpCommand;
import org.sus.command.command.misc.AvatarCommand;
import org.sus.command.command.misc.PingCommand;
import org.sus.command.command.misc.UserInfoCommand;
import org.sus.command.command.music.*;
import org.sus.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {

    private final List<ICommand> commands = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager() {
        add(new BanCommand());
        add(new PingCommand());
        add(new ClearCommand());
        add(new AvatarCommand());
        add(new ShutdownCommand());
        add(new UserInfoCommand());
        add(new SetFortniteCommand());
        add(new FortniteCommand());
        add(new ClearFortniteCommand());
        add(new KickCommand());
        add(new SetLogChannelCommand());
        add(new PlayCommand());
        add(new JoinCommand());
        add(new StopCommand());
        add(new SkipCommand());
        add(new QueueCommand());
        add(new NowPlayingCommand());
        add(new LeaveCommand());
        add(new RepeatCommand());
        add(new HelpCommand(this));
    }

    private void add(ICommand command) {
        boolean found = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(command.getName()));

        if(found) {
            throw new IllegalArgumentException("There are two commands with the same name.");
        }

        log.info("Registering command " + command.getName());

        commands.add(command);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand get(String search) {
        String lower = search.toLowerCase();

        for(ICommand cmd : commands) {
            if(cmd.getName().equals(lower) || cmd.getAliases().contains(search)) {
                return cmd;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent e) {
        String[] split = e.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Constants.PREFIX), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.get(invoke);

        if(cmd != null) {
            e.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(e, args);
            cmd.handle(ctx);
        }
    }

}
