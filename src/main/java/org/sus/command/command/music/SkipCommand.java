package org.sus.command.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.lavaplayer.GuildMusicManager;
import org.sus.lavaplayer.PlayerManager;

import java.util.Collections;
import java.util.List;

public class SkipCommand implements ICommand {

    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        Member member = ctx.getSelfMember();
        GuildVoiceState state = member.getVoiceState();


        if (!state.inVoiceChannel()) {
            channel.sendMessage("**Eu preciso estar em um canal de voz.**").queue();
            return;
        }

        Member senderMember = ctx.getMember();
        GuildVoiceState senderState = senderMember.getVoiceState();

        if (!senderState.inVoiceChannel()) {
            channel.sendMessage("**Você precisa estar em um canal de voz.**").queue();
            return;
        }

        if (!senderState.getChannel().equals(state.getChannel())) {
            channel.sendMessage("**Você precisa estar no mesmo canal de voz que eu.**").queue();
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            ctx.getChannel().sendMessage("**Não tem nenhuma música tocando agora.**").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        ctx.getChannel().sendMessage("**Música pulada com sucesso.**").queue();

    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "pula a música atual";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("pular");
    }
}
