package org.sus.command.command.fun;

import org.sus.Main;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.object.FortnitePlayer;

import java.util.Collections;
import java.util.List;

public class ClearFortniteCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        if(!Main.getFortnitePlayerDAO().exists(ctx.getAuthor().getId())) {
            ctx.getChannel().sendMessage("**Você ainda não está cadastrado no banco de dados.**").queue();
            return;
        }

        FortnitePlayer fp = Main.getFortnitePlayerDAO().getById(ctx.getAuthor().getId());

        Main.getFortnitePlayerDAO().delete(fp);

        ctx.getChannel().sendMessage("**Deletado com sucesso.**").queue();

    }

    @Override
    public String getName() {
        return "clearfortnite";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("limparfortnite");
    }
}
