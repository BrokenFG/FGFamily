package ru.brfg.fgfamily.Commands.marry;

import org.bukkit.entity.Player;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryAccept
extends BSubCommand {
    public CmdMarryAccept() {
        this.aliases.add("accept");
        this.aliases.add("a");
        this.permission = "marry.accept";
        this.senderMustBeInMarry = false;
        this.senderMustBeWithoutMarry = true;
        this.senderMustBePlayer = true;
    }

    @Override
    public void execute() {
        if (!this.main.marryManager.isInvited(this.player)) {
            this.msg(Lang.MSG_ACCEPT_NOTINVITED.toMsg());
            return;
        }
        Player player = this.main.marryManager.getInvitedBy(this.player);
        this.main.marryManager.removeInvitation(this.player);
        this.main.marryManager.addMarry(player, this.player);
        this.main.marryManager.broadcast(Lang.MSG_ACCEPT_ACCEPTED.toMsg().replace("%player%", player.getName()).replace("%player1%", this.player.getName()));
    }
}

