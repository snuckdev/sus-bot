package org.sus.database;

import org.sus.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtil {

    public void createTable(String tableName, String columns) {
        try(Connection connection = Main.getHikari().getConnection()) {
            PreparedStatement st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ")");
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
