package ru.brfg.fgfamily.Commands.marry;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Config;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarryInvite
extends BSubCommand {
    public CmdMarryInvite() {
        this.aliases.add("invite");
        this.aliases.add("inv");
        this.correctUsage = "/marry invite <\u0438\u0433\u0440\u043e\u043a>";
        this.permission = "marry.invite";
        this.senderMustBeInMarry = false;
        this.senderMustBeWithoutMarry = true;
        this.senderMustBePlayer = true;
    }

    @Override
    public void execute() {
        if (this.args.length < 1) {
            this.sendCorrectUsage();
            return;
        }
        Player player = this.main.getServer().getPlayer(this.args[0]);
        if (player == null) {
            this.msg(Lang.MSG_INVALIDPLAYER.toMsg());
            return;
        }
        if (this.player.equals((Object)player)) {
            this.msg(Lang.MSG_INVITE_OWNSELF.toMsg());
            return;
        }
        if (this.main.marryManager.isMarried(player)) {
            this.msg(Lang.MSG_INVITE_ALREADYMARRIED.toMsg());
            return;
        }
        if (this.main.marryManager.isSendInvite(this.player)) {
            this.msg(Lang.MSG_INVITE_ALREADYSEND.toMsg());
            return;
        }
        if (this.main.marryManager.isInvited(player)) {
            this.msg(Lang.MSG_INVITE_ALREADYINVITED.toMsg());
            return;
        }
        if (!this.main.economy.has((OfflinePlayer)this.player, (double)Config.marryPrice)) {
            this.msg(Lang.MSG_INVITE_NOMONEY.toMsg().replace("%money%", String.valueOf(Config.marryPrice)));
            return;
        }
        this.main.marryManager.addInvitation(this.player, player);
        this.msg(Lang.MSG_INVITE_INVITED_INVITED.toMsg().replace("%player%", player.getName()));
        player.sendMessage(Lang.MSG_INVITE_INVITED_BY.toMsg().replace("%player%", this.player.getName()));
    }
}

