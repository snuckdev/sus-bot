package org.sus.command.command.administration;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import java.util.Collections;
import java.util.List;

public class KickCommand implements ICommand {


    @Override
    public void handle(CommandContext ctx) {

        User author = ctx.getAuthor();
        Member member = ctx.getMember();

        if(!member.hasPermission(Permission.KICK_MEMBERS)) {
            ctx.getChannel().sendMessageFormat("**Você não tem a permissão** `%s`!", Permission.KICK_MEMBERS.getName()).queue();
            return;
        }

        if(ctx.getArgs().size() == 0) {
            ctx.getChannel().sendMessage("**Uso correto:** `!kick <menção> [motivo]`").queue();
        }

        if(ctx.getArgs().size() == 1) {

            List<Member> mentioned = ctx.getMessage().getMentionedMembers();

            if(mentioned.size() == 0) {
                ctx.getChannel().sendMessage("**Mencione alguem para kickar.**").queue();
                return;
            }

            Member memberToBan = mentioned.get(0);

            if(memberToBan.getUser().getId().equals(ctx.getJDA().getSelfUser().getId())) {
                ctx.getChannel().sendMessage("**Eu não posso me expulsar.**").queue();
                return;
            }

            memberToBan.kick().queue();
            ctx.getChannel().sendMessageFormat("❌ `%s` **foi expulso por** `%s`", memberToBan.getUser().getAsTag(), author.getAsTag()).queue();

        } else if(ctx.getArgs().size() >= 2) {

            List<Member> mentioned = ctx.getMessage().getMentionedMembers();

            if(mentioned.size() == 0) {
                ctx.getChannel().sendMessage("**Mencione alguem para kickar.**").queue();
                return;
            }

            Member memberToBan = mentioned.get(0);
            String reason = String.join(" ", ctx.getArgs().subList(1, ctx.getArgs().size()));

            if(memberToBan.getUser().getId().equals(ctx.getJDA().getSelfUser().getId())) {
                ctx.getChannel().sendMessage("**Eu não posso me expulsar.**").queue();
                return;
            }

            memberToBan.kick(reason).queue();

            ctx.getChannel().sendMessageFormat("❌ `%s` **foi expulso por** `%s` **- motivo:** `%s`", memberToBan.getUser().getAsTag(), author.getAsTag(), reason).queue();
        }

    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "expulsa um usuário do servidor";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("kickar");
    }
}
