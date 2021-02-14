package org.sus.command.command.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import java.awt.*;
import java.time.ZonedDateTime;

public class PingCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue((ping) -> {

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("\uD83C\uDFD3 meu ping");
            embedBuilder.setColor(Color.ORANGE);
            embedBuilder.addField("Rest", ping.toString() + "ms", true);
            embedBuilder.addField("WS", jda.getGatewayPing() + "ms", true);
            embedBuilder.setFooter(ctx.getAuthor().getAsTag(), ctx.getAuthor().getEffectiveAvatarUrl());
            embedBuilder.setTimestamp(ZonedDateTime.now());

            ctx.getChannel().sendMessage(embedBuilder.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "mostra o ping do bot";
    }
}
