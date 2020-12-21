package ru.brfg.fgfamily;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import ru.brfg.fgfamily.FGFamily;
import ru.brfg.fgfamily.utlis.Config;
import ru.brfg.fgfamily.utlis.Lang;
import ru.brfg.fgfamily.utlis.ParticleEffect;

public class PlayerListener
implements Listener {
    private List<Player> cd = new ArrayList<Player>();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (FGFamily.getInstance().marryManager.isInvited(e.getPlayer())) {
            FGFamily.getInstance().marryManager.getInvitedBy(e.getPlayer()).sendMessage(Lang.MSG_DECLINE_DECLINED_BY.toMsg().replace("%player%", e.getPlayer().getName()));
        }
        FGFamily.getInstance().marryManager.removeInvitation(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        if (!e.getPlayer().isSneaking()) {
            return;
        }
        Player player = e.getPlayer();
        Player player1 = (Player)e.getRightClicked();
        if (this.cd.contains((Object)player)) {
            return;
        }
        if (!FGFamily.getInstance().marryManager.isMarried(player, player1)) {
            return;
        }
        this.cd.add(player);
        Bukkit.getScheduler().runTaskLater((Plugin)FGFamily.getInstance(), () -> this.cd.remove((Object)player), 200L);
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() != player.getWorld()) continue;
            if (!(player.getLocation().distance(p.getLocation()) <= 10.0)) continue;
            playerList.add(p);
        }
        for (Player p : playerList) {
            p.sendMessage(Lang.MSG_KISS.toString().replace("%player%", player.getName()).replace("%player1%", player1.getName()));
        }
        AtomicInteger i = new AtomicInteger(0);
        ParticleEffect.HEART.display(0.5f, 0.1f, 0.5f, 0.05f, 30, player.getLocation().clone().add(0.0, 2.0, 0.0), 128.0);
        ParticleEffect.HEART.display(0.5f, 0.1f, 0.5f, 0.05f, 30, player1.getLocation().clone().add(0.0, 2.0, 0.0), 128.0);
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        Player player2;
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player1 = (Player)e.getDamager();
        if (!FGFamily.getInstance().marryManager.isMarried(player1, player2 = (Player)e.getEntity())) {
            return;
        }
        if (!FGFamily.getInstance().marryManager.getPlayersMarry(player1).isPvP()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!FGFamily.getInstance().marryManager.isMarried(e.getPlayer())) {
            e.setFormat(e.getFormat().replaceAll("!marry!", ""));
        } else {
            e.setFormat(e.getFormat().replaceAll("!marry!", Config.marry.replace("&", "\u00a7")));
        }
    }
}

