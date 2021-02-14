package org.sus.command.command.help;

import net.dv8tion.jda.api.entities.TextChannel;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.command.manager.CommandManager;

import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append("**Comandos do Sus Bot** :flushed:\n");

            manager.getCommands().stream().map(ICommand::getName).forEach((it) -> {
                builder.append("`!")
                        .append(it)
                        .append("`\n");
            });

            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.get(search);

        if(command == null) {
            channel.sendMessage("**Este comando não foi encontrado.**").queue();
            return;
        }

        channel.sendMessage("**" + command.getHelp() + "**").queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("ajuda", "sus");
    }

    @Override
    public String getHelp() {
        return "mostra os comandos do bot.\n" +
                "Uso: `!help [comando]`";
    }
}
