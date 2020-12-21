package ru.brfg.fgfamily;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import ru.brfg.fgfamily.FGFamily;
import ru.brfg.fgfamily.Marry;
import ru.brfg.fgfamily.utlis.Config;
import ru.brfg.fgfamily.utlis.Lang;
import ru.brfg.fgfamily.utlis.MarrySort;
import ru.brfg.fgfamily.utlis.SQL;

public class MarryManager {
    private FGFamily main;
    private Map<Player, Player> invites;
    private Map<Player, BukkitTask> taskMap;

    public MarryManager(FGFamily main) {
        this.main = main;
        this.invites = new HashMap<Player, Player>();
        this.taskMap = new HashMap<Player, BukkitTask>();
    }

    private Marry getMarry(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            String player1 = resultSet.getString("player1");
            String player2 = resultSet.getString("player2");
            boolean pvp = resultSet.getBoolean("pvp");
            long time = resultSet.getLong("time");
            return new Marry(player1, player2, pvp, time);
        }
        return null;
    }

    public Marry getPlayersMarry(Player player) {
        try {
            Statement statement = SQL.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from `marry` where `player1` = '" + player.getName() + "' or `player2` = '" + player.getName() + "';");
            return this.getMarry(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isMarried(Player player) {
        try {
            Statement statement = SQL.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from `marry` where `player1` = '" + player.getName() + "' or `player2` = '" + player.getName() + "';");
            return resultSet.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isInvited(Player player) {
        return this.invites.containsKey((Object)player) || this.invites.containsValue((Object)player);
    }

    public void addInvitation(Player player, Player player1) {
        this.invites.put(player, player1);
        this.main.economy.withdrawPlayer((OfflinePlayer)player, (double)Config.marryPrice);
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> this.removeInvitation(player, true), 600L);
        this.taskMap.put(player, bukkitTask);
    }

    public Player getInvitedBy(Player player) {
        if (!this.invites.containsValue((Object)player)) {
            return null;
        }
        for (Player player1 : this.invites.keySet()) {
            if (!this.invites.get((Object)player1).equals((Object)player)) continue;
            return player1;
        }
        return null;
    }

    public void addMarry(Player player, Player player1) {
        try {
            Statement statement = SQL.getConnection().createStatement();
            statement.executeUpdate("insert into `marry` values ('" + player.getName() + "', '" + player1.getName() + "', 'false', '" + System.currentTimeMillis() + "');");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(msg);
        }
    }

    public boolean isSendInvite(Player player) {
        return this.invites.containsKey((Object)player);
    }

    public void removeInvitation(Player player) {
        this.removeInvitation(player, false);
    }

    public void removeInvitation(Player player, boolean task) {
        Player player1 = this.invites.remove((Object)player);
        if (player1 == null) {
            for (Player player2 : this.invites.keySet()) {
                if (!this.invites.get((Object)player2).equals((Object)player)) continue;
                player1 = player2;
                break;
            }
            if (player1 != null) {
                this.invites.remove((Object)player1);
                if (this.taskMap.containsKey((Object)player1)) {
                    this.taskMap.get((Object)player1).cancel();
                    this.taskMap.remove((Object)player1);
                }
                this.main.economy.depositPlayer((OfflinePlayer)player1, (double)Config.marryPrice);
            }
        } else {
            if (this.taskMap.containsKey((Object)player)) {
                this.taskMap.get((Object)player).cancel();
                this.taskMap.remove((Object)player);
            }
            this.main.economy.depositPlayer((OfflinePlayer)player, (double)Config.marryPrice);
            if (task) {
                player.sendMessage(Lang.MSG_INVITE_NOTIME.toMsg());
            }
        }
    }

    public Marry removeMarry(Player player) {
        try {
            Statement statement = SQL.getConnection().createStatement();
            Marry marry = this.getPlayersMarry(player);
            statement.executeUpdate("delete from `marry` where `player1` = '" + player.getName() + "' or `player2` = '" + player.getName() + "';");
            return marry;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTime(long ms) {
        long minutes;
        StringBuilder sb = new StringBuilder(40);
        ms = System.currentTimeMillis() / 1000L - ms / 1000L;
        if (ms / 31449600L > 0L) {
            minutes = ms / 31449600L;
            if (minutes > 100L) {
                return "\u041d\u0438\u043a\u043e\u0433\u0434\u0430";
            }
            sb.append(minutes + " " + Lang.MSG_YEARS.toString() + " ");
            ms -= minutes * 31449600L;
        }
        if (ms / 2620800L > 0L) {
            minutes = ms / 2620800L;
            sb.append(minutes + " " + Lang.MSG_MONTH.toString() + " ");
            ms -= minutes * 2620800L;
        }
        if (ms / 604800L > 0L) {
            minutes = ms / 604800L;
            sb.append(minutes + " " + Lang.MSG_WEEKS.toString() + " ");
            ms -= minutes * 604800L;
        }
        if (ms / 86400L > 0L) {
            minutes = ms / 86400L;
            sb.append(minutes + " " + Lang.MSG_DAYS.toString() + " ");
            ms -= minutes * 86400L;
        }
        if (ms / 3600L > 0L) {
            minutes = ms / 3600L;
            sb.append(minutes + " " + Lang.MSG_HOURS.toString() + " ");
            ms -= minutes * 3600L;
        }
        if (ms / 60L > 0L) {
            minutes = ms / 60L;
            sb.append(minutes + " " + Lang.MSG_MINUTES.toString() + " ");
            ms -= minutes * 60L;
        }
        if (ms > 0L) {
            sb.append(ms + " " + Lang.MSG_SECONDS.toString() + " ");
        }
        if (sb.length() > 1) {
            sb.replace(sb.length() - 1, sb.length(), "");
        } else {
            sb = new StringBuilder("N/A");
        }
        return sb.toString();
    }

    public List<Marry> getMarries(MarrySort marrySort) {
        try {
            Marry marry;
            Statement statement = SQL.getConnection().createStatement();
            String query = "select * from `marry` ";
            query = marrySort == MarrySort.TIME ? query + "order by `time` asc;" : (marrySort == MarrySort.MONEY ? query + "order by `money` desc;" : query + ";");
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Marry> marryList = new ArrayList<Marry>();
            while ((marry = this.getMarry(resultSet)) != null) {
                marryList.add(marry);
            }
            return marryList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Marry>();
        }
    }

    public boolean isMarried(Player player1, Player player2) {
        try {
            Statement statement = SQL.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from `marry` where (`player1` = '" + player1.getName() + "' and `player2` = '" + player2.getName() + "') or (`player1` = '" + player2.getName() + "' and `player2` = '" + player1.getName() + "');");
            return resultSet.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

