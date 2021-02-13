package org.sus.database.dao;

import org.sus.Main;
import org.sus.object.FortnitePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FortnitePlayerDAO {

    public void save(FortnitePlayer fp) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO fortnite_player (id, name, platform) VALUES (?, ?, ?)");
            st.setString(1, fp.getId());
            st.setString(2, fp.getName());
            st.setString(3, fp.getPlatform());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(FortnitePlayer fp) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE fortnite_player SET name = ?, platform = ? WHERE id = ?");
            st.setString(1, fp.getName());
            st.setString(2, fp.getPlatform());
            st.setString(3, fp.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String id) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM fortnite_player WHERE id = ?");
            st.setString(1, id);

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

    public void delete(FortnitePlayer fp) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("DELETE FROM fortnite_player WHERE id = ?");
            st.setString(1, fp.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FortnitePlayer> findAll() {
        List<FortnitePlayer> result = new ArrayList<>();
        try(Connection connection = Main.getHikari().getConnection()) {

            PreparedStatement st = connection.prepareStatement("SELECT * FROM fortnite_player");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                result.add(new FortnitePlayer(rs.getString("id"), rs.getString("name"), rs.getString("platform")));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public FortnitePlayer getById(String id) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM fortnite_player WHERE id = ?");
            st.setString(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                FortnitePlayer fp =  new FortnitePlayer(rs.getString("id"), rs.getString("name"), rs.getString("platform"));
                st.close();
                rs.close();
                return fp;
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
