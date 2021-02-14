package org.sus.command.command.administration;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.sus.Main;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.database.dao.LogChannelDAO;
import org.sus.object.LogChannel;

import java.util.Collections;
import java.util.List;

public class SetLogChannelCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        Member authorMember = ctx.getMember();

        if(!authorMember.hasPermission(Permission.ADMINISTRATOR)) {
            ctx.getChannel().sendMessageFormat("**Você não tem a permissão** `%s`", Permission.ADMINISTRATOR.getName()).queue();
            return;
        }

        LogChannelDAO dao = Main.getLogChannelDAO();

        if(dao.exists(ctx.getGuild().getId())) {
            ctx.getChannel().sendMessageFormat("**Este servidor já tem o canal <#%s> registrado no banco de dados.**", dao.getByGuildId(ctx.getGuild().getId()).getChannelId()).queue();
            return;
        }

        if(ctx.getArgs().size() == 0) {
            ctx.getChannel().sendMessage("**Uso correto:** `!setlog <menção do canal>.`").queue();
            return;
        }

        if(ctx.getMessage().getMentionedChannels().size() == 0) {
            ctx.getChannel().sendMessage("**Você precisa mencionar um canal.**").queue();
            return;
        }


        dao.save(new LogChannel(ctx.getGuild().getId(), ctx.getMessage().getMentionedChannels().get(0).getId()));
        ctx.getChannel().sendMessage("**Salvo no banco de dados.**").queue();

    }

    @Override
    public String getName() {
        return "setlog";
    }

    @Override
    public String getHelp() {
        return "seta o canal de logs do servidor";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("setlogs");
    }
}
