package org.sus.event;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.sus.Main;
import org.sus.database.dao.CachedMessageDAO;
import org.sus.database.dao.LogChannelDAO;
import org.sus.object.CachedMessage;

public class MessageReceiveListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent e) {
        String content = e.getMessage().getContentRaw();

        LogChannelDAO dao = Main.getLogChannelDAO();

        if(dao.exists(e.getGuild().getId())) {
            if(e.getMessage().getEmbeds().size() == 0)  {
                CachedMessageDAO cacheDao = Main.getCachedMessageDAO();
                cacheDao.save(new CachedMessage(e.getMessageId(), content.trim(), e.getAuthor().getAsTag()));
            }
        }
    }
}
