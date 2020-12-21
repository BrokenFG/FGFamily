package ru.brfg.fgfamily.Commands.marry;

import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryPvp
extends BSubCommand {
    public CmdMarryPvp() {
        this.aliases.add("pvp");
        this.permission = "marry.pvp";
        this.senderMustBeInMarry = true;
        this.senderMustBePlayer = true;
        this.senderMustBeWithoutMarry = false;
    }

    @Override
    public void execute() {
        if (this.marry.togglePvP()) {
            this.msg(Lang.MSG_PVP_ENABLE.toMsg());
        } else {
            this.msg(Lang.MSG_PVP_DISABLE.toMsg());
        }
    }
}

