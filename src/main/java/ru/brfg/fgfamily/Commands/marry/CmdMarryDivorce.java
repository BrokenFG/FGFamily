package ru.brfg.fgfamily.Commands.marry;

import org.bukkit.OfflinePlayer;
import ru.brfg.fgfamily.Marry;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Config;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryDivorce
extends BSubCommand {
    public CmdMarryDivorce() {
        this.aliases.add("divorce");
        this.permission = "marry.divorce";
        this.senderMustBeInMarry = true;
        this.senderMustBeWithoutMarry = false;
        this.senderMustBePlayer = true;
    }

    @Override
    public void execute() {
        if (!this.main.economy.has((OfflinePlayer)this.player, (double)Config.divorcePrice)) {
            this.msg(Lang.MSG_DIVORCE_NOMONEY.toMsg().replace("%money%", String.valueOf(Config.divorcePrice)));
            return;
        }
        Marry marry = this.main.marryManager.removeMarry(this.player);
        String name = marry.getPartner(this.player.getName());
        this.main.marryManager.broadcast(Lang.MSG_DIVORCE_DIVORCED.toMsg().replace("%player%", this.player.getName()).replace("%player1%", name));
    }
}

