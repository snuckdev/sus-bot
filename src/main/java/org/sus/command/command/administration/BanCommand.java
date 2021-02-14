package org.sus.command.command.administration;

import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Collections;
import java.util.List;

public class BanCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        Member member = ctx.getMember();

        if(!(ctx.getArgs().size() >= 2)) {
            ctx.getChannel().sendMessage("**Comando correto:** `!ban <menção> <motivo>`").queue();
            return;
        }

        if (!member.hasPermission(Permission.BAN_MEMBERS)) {
            ctx.getChannel().sendMessageFormat("**Você não possui a permissão:** `%s`", Permission.BAN_MEMBERS.getName()).queue();
            return;
        }


        List<Member> mentioned = ctx.getMessage().getMentionedMembers();

        if(mentioned.size() == 0) {
            ctx.getChannel().sendMessage("**Comando correto:** `!ban <menção> <motivo>`").queue();
            return;
        }

        Member target = mentioned.get(0);
        String realTarget = target.getEffectiveName();

        String reason = String.join(" ", ctx.getArgs().subList(1, ctx.getArgs().size()));

        target.ban(0, reason).queue();
        ctx.getChannel().sendMessage(realTarget + " **foi banido! Motivo:** " + reason).queue();
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getHelp() {
        return "bane algum usuário específico";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("banir");
    }
}
