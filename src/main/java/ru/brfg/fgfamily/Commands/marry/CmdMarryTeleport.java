package ru.brfg.fgfamily.Commands.marry;

import org.bukkit.entity.Player;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryTeleport
extends BSubCommand {
    public CmdMarryTeleport() {
        this.aliases.add("teleport");
        this.aliases.add("tp");
        this.permission = "marry.teleport";
        this.senderMustBeInMarry = true;
        this.senderMustBeWithoutMarry = false;
        this.senderMustBePlayer = true;
    }

    @Override
    public void execute() {
        Player player = this.marry.getPlayerPartner(this.player);
        if (player == null) {
            this.msg(Lang.MSG_TELEPORT_OFFLINE.toMsg());
            return;
        }
        this.player.teleport(player.getLocation());
        this.msg(Lang.MSG_TELEPORT_TELEPORTED_TELEPORTED.toMsg().replace("%player%", player.getName()));
        player.sendMessage(Lang.MSG_TELEPORT_TELEPORTED_BY.toMsg().replace("%player%", this.player.getName()));
    }
}

