package ru.brfg.fgfamily.Commands.marry;

import org.bukkit.entity.Player;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Config;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryDecline
extends BSubCommand {
    public CmdMarryDecline() {
        this.aliases.add("decline");
        this.aliases.add("dl");
        this.permission = "marry.decline";
        this.senderMustBeInMarry = false;
        this.senderMustBeWithoutMarry = true;
        this.senderMustBePlayer = true;
    }

    @Override
    public void execute() {
        if (!this.main.marryManager.isInvited(this.player)) {
            this.msg(Lang.MSG_DECLINE_NOTINVITED.toMsg());
            return;
        }
        Player player = this.main.marryManager.getInvitedBy(this.player);
        this.msg(Lang.MSG_DECLINE_DECLINED_DECLINED.toMsg().replace("%player%", player.getName()));
        player.sendMessage(Lang.MSG_DECLINE_DECLINED_BY.toMsg().replace("%player%", this.player.getName()).replace("%money%", String.valueOf(Config.marryPrice)));
        this.main.marryManager.removeInvitation(player);
    }
}

