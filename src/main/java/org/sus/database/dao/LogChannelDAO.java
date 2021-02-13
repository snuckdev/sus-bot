package org.sus.database.dao;

import org.sus.Main;
import org.sus.object.LogChannel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogChannelDAO {

    public void save(LogChannel logChannel) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO log_channels (guild_id, channel_id) VALUES (?, ?)");
            st.setString(1, logChannel.getGuildId());
            st.setString(2, logChannel.getChannelId());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String guildId) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM log_channels WHERE guild_id = ?");
            st.setString(1, guildId);

            ResultSet rs = st.executeQuery();

            boolean result = rs.next();

            st.close();
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LogChannel getByGuildId(String guildId) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM log_channels WHERE guild_id = ?");
            st.setString(1, guildId);

            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                LogChannel result = new LogChannel(rs.getString("guild_id"), rs.getString("channel_id"));
                st.close();
                rs.close();
                return result;
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(LogChannel logChannel) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("DELETE FROM log_channels WHERE guild_id = ?");
            st.setString(1, logChannel.getGuildId());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(LogChannel logChannel) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE log_channels SET channel_id = ? WHERE guild_id = ?");
            st.setString(1, logChannel.getChannelId());
            st.setString(2, logChannel.getGuildId());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
