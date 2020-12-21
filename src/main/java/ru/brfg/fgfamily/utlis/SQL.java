package ru.brfg.fgfamily.utlis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ru.brfg.fgfamily.utlis.Config;

public class SQL {
    private static Connection connection = null;

    public static Connection getConnection() {
        SQL.connect();
        return connection;
    }

    public static void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://" + Config.host + ":" + Config.port + "/" + Config.database + "?useUnicode=true&characterEncoding=utf-8", Config.user, Config.password);
                connection.setAutoCommit(true);
            }
        }
        catch (Exception var1) {
            var1.printStackTrace();
        }
    }

    public static void createTables() {
        SQL.connect();
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `marry` (`player1` text(16) NOT NULL,`player2` text(16) NOT NULL, `pvp` text(16) NOT NULL, `time` bigint NOT NULL)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

