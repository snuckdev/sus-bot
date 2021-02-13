package org.sus.database.dao;

import org.sus.Main;
import org.sus.object.CachedMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CachedMessageDAO {

    public void save(CachedMessage cachedMessage) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO log_messages (message_id, content, author_tag) VALUES (?, ?, ?)");
            st.setString(1, cachedMessage.getMessageId());
            st.setString(2, cachedMessage.getRawContent());
            st.setString(3, cachedMessage.getAuthor());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String messageId) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM log_messages WHERE message_id = ?");
            st.setString(1, messageId);

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

    public void delete(String messageId) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("DELETE FROM log_messages WHERE message_id = ?");
            st.setString(1, messageId);

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("DELETE FROM log_messages WHERE 1");
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CachedMessage findById(String messageId) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM log_messages WHERE message_id = ?");
            st.setString(1, messageId);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                CachedMessage result = new CachedMessage(rs.getString("message_id"), rs.getString("content"), rs.getString("author_tag"));
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

}
