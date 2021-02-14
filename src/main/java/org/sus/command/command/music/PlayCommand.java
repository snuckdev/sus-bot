package org.sus.command.command.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.lavaplayer.PlayerManager;

import java.net.URI;
import java.net.URISyntaxException;

public class PlayCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {

        TextChannel channel = ctx.getChannel();
        Member member = ctx.getSelfMember();
        GuildVoiceState state = member.getVoiceState();

        if(ctx.getArgs().isEmpty()) {
            channel.sendMessage("**Uso correto:** `!play <url ou pesquisa>`").queue();
            return;
        }

        if(!state.inVoiceChannel()) {
            channel.sendMessage("**Eu preciso estar em um canal de voz.**").queue();
            return;
        }

        Member senderMember = ctx.getMember();
        GuildVoiceState senderState = senderMember.getVoiceState();

        if(!senderState.inVoiceChannel()) {
            channel.sendMessage("**Você precisa estar em um canal de voz.**").queue();
            return;
        }

        if(!senderState.getChannel().equals(state.getChannel())) {
            channel.sendMessage("**Você precisa estar no mesmo canal de voz que eu.**").queue();
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if(!isUrl(link)) {
            link = "ytsearch:" + link;
        }
        PlayerManager.getInstance()
                .loadAndPlay(channel, link);
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "toca uma música do YouTube";
    }
}
