package org.sus;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.sus.database.DatabaseUtil;
import org.sus.database.dao.CachedMessageDAO;
import org.sus.database.dao.FortnitePlayerDAO;
import org.sus.database.dao.LogChannelDAO;
import org.sus.event.CommandListener;
import org.sus.event.MessageDeleteListener;
import org.sus.event.MessageReceiveListener;
import org.sus.task.ClearLogTask;
import org.sus.util.Constants;

import javax.security.auth.login.LoginException;

public class Main {

    @Getter private static HikariDataSource hikari;
    private static final DatabaseUtil util = new DatabaseUtil();
    @Getter private static final FortnitePlayerDAO fortnitePlayerDAO = new FortnitePlayerDAO();
    @Getter private static final LogChannelDAO logChannelDAO = new LogChannelDAO();
    @Getter private static final CachedMessageDAO cachedMessageDAO = new CachedMessageDAO();
    @Getter private static final Dotenv dotenv = Dotenv.load();

    public static void main(String[] args) throws LoginException {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/bot");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikari = new HikariDataSource(config);

        setupDatabase();

        JDABuilder builder = JDABuilder.createDefault(Constants.TOKEN);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.disableIntents(GatewayIntent.GUILD_MESSAGE_TYPING);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.streaming("who is the sus? \uD83D\uDE33", "https://twitch.tv/sus/"));
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new MessageDeleteListener());
        builder.addEventListeners(new MessageReceiveListener());

        builder.build();
        ClearLogTask.start();
    }

    private static void setupDatabase() {
        util.createTable("fortnite_player", "id varchar(32), name varchar(64), platform varchar(4)");
        util.createTable("log_messages", "message_id varchar(32), author_tag varchar(32), content TEXT");
        util.createTable("log_channels", "guild_id varchar(32), channel_id varchar(32)");
    }
}
