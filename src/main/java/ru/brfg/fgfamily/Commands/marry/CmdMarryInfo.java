package ru.brfg.fgfamily.Commands.marry;

import org.bukkit.entity.Player;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryInfo
extends BSubCommand {
    public CmdMarryInfo() {
        this.aliases.add("info");
        this.aliases.add("i");
        this.permission = "marry.info";
        this.correctUsage = "/marry info <\u0438\u0433\u0440\u043e\u043a>";
        this.senderMustBeInMarry = false;
        this.senderMustBePlayer = false;
        this.senderMustBeWithoutMarry = false;
    }

    @Override
    public void execute() {
        if (this.args.length == 0) {
            this.sendCorrectUsage();
            return;
        }
        Player player = this.main.getServer().getPlayer(this.args[0]);
        if (player == null) {
            this.msg(Lang.MSG_INVALIDPLAYER.toMsg());
            return;
        }
        String info = Lang.MSG_INFO_INFO.toString().replace("%player%", player.getName()).replace("%status%", this.main.marryManager.isMarried(player) ? Lang.MSG_INFO_STATUS_MARRIED.toString() : Lang.MSG_INFO_STATUS_NOTMARRIED.toString()).replace("%partner%", this.main.marryManager.isMarried(player) ? this.main.marryManager.getPlayersMarry(player).getPartner(player) : Lang.MSG_INFO_NOPARTNER.toString());
        this.msg(info);
    }
}

