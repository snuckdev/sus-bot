package org.sus.command.command.administration;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ClearCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().size() == 0) {
            ctx.getChannel().sendMessage("É !clear <mensagens> pora.").queue();
            return;
        }

        String toDelete = ctx.getArgs().get(0);

        if(!isInteger(toDelete)) {
            ctx.getChannel().sendMessage("Digite apenas números válidos.").queue();
            return;
        }

        int num = Integer.parseInt(toDelete);

        if(num < 2 || num > 100) {
            ctx.getChannel().sendMessage("Eu só posso deletar de 2 a 100 mensagens.").queue();
            return;
        }

        Member member = ctx.getMember();

        if(!member.hasPermission(Permission.MESSAGE_MANAGE)) {
            ctx.getChannel().sendMessageFormat("`Você não tem a permissão %s!`", Permission.MESSAGE_MANAGE.getName()).queue();
            return;
        }

        MessageHistory history = new MessageHistory(ctx.getChannel());
        List<Message> messages;

        messages = history.retrievePast(num).complete();
        ctx.getChannel().deleteMessages(messages).queue();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.CYAN);
        builder.setTitle("\uD83E\uDE84 **Limpador de mensagens**");
        builder.addField("**Quantia de mensagens limpas**", toDelete, true);
        builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getAvatarUrl());

        ctx.getChannel().sendMessage(builder.build()).queue();
    }

    private boolean isInteger(String toTest) {
        try {
            Integer.parseInt(toTest);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("limpar");
    }
}
