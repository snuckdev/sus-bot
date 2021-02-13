package org.sus.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.sus.Main;
import org.sus.database.dao.CachedMessageDAO;
import org.sus.database.dao.LogChannelDAO;
import org.sus.object.CachedMessage;

import java.awt.*;

public class MessageDeleteListener extends ListenerAdapter {

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent e) {
        LogChannelDAO dao = Main.getLogChannelDAO();
        CachedMessageDAO cachedMessageDAO = Main.getCachedMessageDAO();

        if (dao.exists(e.getGuild().getId())) {

            TextChannel logChannel = e.getGuild().getTextChannelById(dao.getByGuildId(e.getGuild().getId()).getChannelId());

            if(cachedMessageDAO.exists(e.getMessageId())) {

                CachedMessage delMessage = cachedMessageDAO.findById(e.getMessageId());

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("✉️ **Logs da Staff**");
                builder.setColor(Color.CYAN);
                builder.addField("\uD83D\uDCDD **Mensagem deletada**", String.format("```%s```", delMessage.getRawContent()), false);
                builder.addField("\uD83D\uDD0D **Autor**", String.format("```%s```", delMessage.getAuthor()), false);

                cachedMessageDAO.delete(e.getMessageId());

                if (logChannel != null) logChannel.sendMessage(builder.build()).queue();
            }

        }
    }
}
