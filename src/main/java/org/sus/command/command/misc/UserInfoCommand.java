package org.sus.command.command.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserInfoCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        List<String> args = ctx.getArgs();
        User target;

        if (args.size() == 0) {

            target = ctx.getAuthor();
            Member member = ctx.getMember();

            EmbedBuilder builder = new EmbedBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm");

            builder.addField("\uD83D\uDCD6 Tag do Discord", "`" + target.getAsTag() + "`", true);
            builder.addField("\uD83D\uDCC5 Conta criada em", String.format("`%s`", target.getTimeCreated().format(formatter)), true);
            if(member != null) {
                builder.addField("\uD83D\uDCC5 Entrou no servidor em", String.format("`%s`", member.getTimeJoined().format(formatter)), true);
            }
            builder.addField("\uD83C\uDFF7️ ID", "`" + target.getId() + "`", true);

            builder.setThumbnail(ctx.getAuthor().getEffectiveAvatarUrl());
            builder.setColor(Color.CYAN);

            builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
            builder.setTimestamp(ZonedDateTime.now());

            ctx.getChannel().sendMessage(builder.build()).queue();

        } else if (args.size() == 1) {
            if (ctx.getMessage().getMentionedMembers().size() == 0) {
                try {
                    target = ctx.getJDA().retrieveUserById(args.get(0)).complete();
                    Member member = ctx.getGuild().getMember(target);

                    EmbedBuilder builder = new EmbedBuilder();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm");

                    builder.addField("\uD83D\uDCD6 Tag do Discord", "`" + target.getAsTag() + "`", true);
                    builder.addField("\uD83D\uDCC5 Conta criada em", String.format("`%s`", target.getTimeCreated().format(formatter)), true);

                    if(member != null) {
                        builder.addField("\uD83D\uDCC5 Entrou no servidor em", String.format("`%s`", member.getTimeJoined().format(formatter)), true);
                    }

                    builder.addField("\uD83C\uDFF7️ ID", "`" + target.getId() + "`", true);

                    builder.setThumbnail(ctx.getAuthor().getEffectiveAvatarUrl());
                    builder.setColor(Color.CYAN);

                    builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
                    builder.setTimestamp(ZonedDateTime.now());

                    ctx.getChannel().sendMessage(builder.build()).queue();
                } catch (Exception e) {
                    ctx.getChannel().sendMessage("**Não consegui achar um usuário com este ID.**").queue();
                }
            } else {
                target = ctx.getMessage().getMentionedMembers().get(0).getUser();
                Member member = ctx.getGuild().getMember(target);

                EmbedBuilder builder = new EmbedBuilder();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm");

                builder.addField("\uD83D\uDCD6 Tag do Discord", "`" + target.getAsTag() + "`", true);
                builder.addField("\uD83D\uDCC5 Conta criada em", String.format("`%s`", target.getTimeCreated().format(formatter)), true);
                if(member != null) {
                    builder.addField("\uD83D\uDCC5 Entrou no servidor em", String.format("`%s`", member.getTimeJoined().format(formatter)), true);
                }
                builder.addField("\uD83C\uDFF7️ ID", "`" + target.getId() + "`", true);

                builder.setThumbnail(ctx.getAuthor().getEffectiveAvatarUrl());
                builder.setColor(Color.CYAN);

                builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
                builder.setTimestamp(ZonedDateTime.now());

                ctx.getChannel().sendMessage(builder.build()).queue();
            }

        }
    }


    @Override
    public String getName() {
        return "userinfo";
    }

    @Override
    public String getHelp() {
        return "mostra as informações de um usuário";
    }
}
