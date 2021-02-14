package org.sus.command.command.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

import java.util.Collections;
import java.util.List;

public class LeaveCommand implements ICommand {

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

        AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.closeAudioConnection();

        channel.sendMessage("**Sai do canal de voz com sucesso.**").queue();

    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("sair");
    }
}
