package ru.brfg.fgfamily;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.brfg.fgfamily.utlis.SQL;

public class Marry {
    private String player1;
    private String player2;
    private boolean pvp;
    private long time;

    public Marry(String player1, String player2, boolean pvp, long time) {
        this.player1 = player1.toLowerCase();
        this.player2 = player2.toLowerCase();
        this.pvp = pvp;
        this.time = time;
    }

    public String getPartner(String name) {
        if (this.player1.equals(name.toLowerCase())) {
            return this.player2;
        }
        return this.player1;
    }

    public String getPartner(Player player) {
        String name = player.getName().toLowerCase();
        if (this.player1.equals(name)) {
            return this.player2;
        }
        return this.player1;
    }

    public Player getPlayerPartner(Player player) {
        if (this.player1.equals(player.getName().toLowerCase())) {
            return Bukkit.getPlayer((String)this.player2);
        }
        return Bukkit.getPlayer((String)this.player1);
    }

    public Player getPlayerPartner(String player) {
        if (this.player1.equals(player.toLowerCase())) {
            return Bukkit.getPlayer((String)this.player2);
        }
        return Bukkit.getPlayer((String)this.player1);
    }

    public List<String> getPartners() {
        return Arrays.asList(this.player1, this.player2);
    }

    public long getTime() {
        return this.time;
    }

    public boolean togglePvP() {
        this.pvp = !this.pvp;
        try {
            Statement statement = SQL.getConnection().createStatement();
            if (this.pvp) {
                statement.executeUpdate("update `marry` set `pvp` = 'true' where `player1` = '" + this.player1 + "';");
            } else {
                statement.executeUpdate("update `marry` set `pvp` = 'false' where `player1` = '" + this.player1 + "';");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return this.pvp;
    }

    public boolean isPvP() {
        return this.pvp;
    }
}

