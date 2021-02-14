package org.sus.command.command.administration;

import me.duncte123.botcommons.BotCommons;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.util.Constants;

import java.util.Collections;
import java.util.List;

public class ShutdownCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        if(!ctx.getAuthor().getId().equals(Constants.OWNER_ID)) {
            ctx.getChannel().sendMessage("**Apenas o meu dono pode executar este comando.**").queue();
            return;
        }
        ctx.getChannel().sendMessage("**Desligando...**").queue();
        BotCommons.shutdown(ctx.getJDA());
        ctx.getJDA().shutdown();
    }

    @Override
    public String getName() {
        return "shutdown";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("desligar");
    }
}
