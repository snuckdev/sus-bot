package org.sus.command.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.lavaplayer.GuildMusicManager;
import org.sus.lavaplayer.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class NowPlayingCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        Member member = ctx.getSelfMember();
        GuildVoiceState state = member.getVoiceState();

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

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        AudioPlayer player = musicManager.player;
        AudioTrack playingTrack = player.getPlayingTrack();

        if(playingTrack == null) {
            ctx.getChannel().sendMessage("**Não tem nenhuma música tocando agora.**").queue();
            return;
        }

        AudioTrackInfo info = playingTrack.getInfo();
        channel.sendMessageFormat("\uD83C\uDFB5 **Tocando agora:** `%s` **por** `%s` - (<%s>)", info.title, info.author, info.uri).queue();
    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        return "mostra a música que está tocando agora";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tocando", "np", "tocandoagora");
    }
}
