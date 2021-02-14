package org.sus.command.command.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;

public class JoinCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        Member self = ctx.getSelfMember();
        GuildVoiceState selfState = self.getVoiceState();

        if(selfState.inVoiceChannel()) {
            channel.sendMessage("**Já estou em um canal de voz.**").queue();
            return;
        }

        Member member = ctx.getMember();
        GuildVoiceState state = member.getVoiceState();

        if(!state.inVoiceChannel()) {
            channel.sendMessage("**Você precisa estar em um canal de voz.**").queue();
            return;
        }

        AudioManager manager = ctx.getGuild().getAudioManager();
        VoiceChannel memberChannel = state.getChannel();

        manager.openAudioConnection(memberChannel);
        channel.sendMessageFormat("**Conectando ao canal %s...**", memberChannel.getName()).queue();
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "faz eu entrar em um canal de voz";
    }
}
