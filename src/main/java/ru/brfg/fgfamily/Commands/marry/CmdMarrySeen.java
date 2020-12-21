package ru.brfg.fgfamily.Commands.marry;

import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarrySeen
extends BSubCommand {
    public CmdMarrySeen() {
        this.aliases.add("seen");
        this.permission = "marry.seen";
        this.senderMustBeInMarry = true;
        this.senderMustBePlayer = true;
        this.senderMustBeWithoutMarry = false;
    }

    @Override
    public void execute() {
        boolean online;
        boolean bl = online = this.marry.getPlayerPartner(this.player) != null;
        if (online) {
            this.msg(Lang.MSG_SEEN_ONLINE.toMsg());
            return;
        }
        long time = this.main.essentials.getUser(this.marry.getPartner(this.player)).getLastLogout();
        this.msg(Lang.MSG_SEEN_OFFLINE.toMsg().replace("%time%", this.main.marryManager.getTime(time)));
    }
}

