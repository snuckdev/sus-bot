package org.sus.command.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.sus.command.CommandContext;
import org.sus.command.ICommand;
import org.sus.lavaplayer.GuildMusicManager;
import org.sus.lavaplayer.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommand {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if(queue.isEmpty()) {
            channel.sendMessage("**A fila atual está vazia.**").queue();
            return;
        }


        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> trackList = new ArrayList<>(queue);
        MessageAction messageAction = channel.sendMessage("**Fila atual:**\n");

        for(int i = 0; i < trackCount; i++) {
            AudioTrack track = trackList.get(i);
            AudioTrackInfo info = track.getInfo();

            messageAction.append("#")
                    .append(String.valueOf(i + 1))
                    .append(" `")
                    .append(info.title)
                    .append("` by `")
                    .append(info.author)
                    .append("` [`")
                    .append(formatTime(track.getDuration()))
                    .append("`]\n");
        }

        if(trackList.size() > trackCount) {
            messageAction.append("**E** `")
                    .append(String.valueOf(trackList.size() - trackCount))
                    .append("` **músicas a mais**");
        }

        messageAction.queue();
    }

    private String formatTime(long duration) {
        long hours = duration / TimeUnit.HOURS.toMillis(1);
        long minutes = duration / TimeUnit.MINUTES.toMillis(1);
        long seconds = duration % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("fila");
    }
}
