package org.sus.command.command.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class AvatarCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();

        if (args.size() == 0) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.ORANGE);
            builder.setImage(ctx.getAuthor().getAvatarUrl() + "?size=2048");
            builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
            builder.setTimestamp(ZonedDateTime.now());
            ctx.getChannel().sendMessage(builder.build()).queue();
        } else {

            if (ctx.getMessage().getMentionedMembers().size() == 0) {
                try {

                    ctx.getJDA().retrieveUserById(args.get(0)).queue((user) -> {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setColor(Color.ORANGE);
                        builder.setImage(user.getEffectiveAvatarUrl() + "?size=2048");
                        builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
                        builder.setTimestamp(ZonedDateTime.now());
                        ctx.getChannel().sendMessage(builder.build()).queue();

                    });
                } catch (Exception e) {
                    ctx.getChannel().sendMessage("**Oops! Não consegui achar um usuário com este ID.**").queue();
                }
                return;
            }

            User target = ctx.getMessage().getMentionedMembers().get(0).getUser();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.ORANGE);
            builder.setImage(target.getEffectiveAvatarUrl() + "?size=2048");
            builder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
            builder.setTimestamp(ZonedDateTime.now());
            ctx.getChannel().sendMessage(builder.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("av");
    }
}
