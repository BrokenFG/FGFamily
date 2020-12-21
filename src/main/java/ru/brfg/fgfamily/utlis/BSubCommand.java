package ru.brfg.fgfamily.utlis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brfg.fgfamily.FGFamily;
import ru.brfg.fgfamily.Marry;
import ru.brfg.fgfamily.utlis.Lang;

public abstract class BSubCommand {
    public FGFamily main = FGFamily.getInstance();
    public List<String> aliases = new ArrayList<String>();
    public CommandSender sender;
    public String[] args;
    public Player player;
    public Marry marry;
    public String correctUsage = "";
    public String permission = "";
    public int requiredRank = 0;
    public boolean senderMustBePlayer;
    public boolean senderMustBeInMarry;
    public boolean senderMustBeWithoutMarry;

    public void execute(CommandSender sender, String[] a) {
        if (sender instanceof Player) {
            this.player = (Player)sender;
            this.marry = this.main.marryManager.getPlayersMarry(this.player);
        } else {
            this.player = null;
            this.marry = null;
        }
        this.sender = sender;
        LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
        list.remove(0);
        this.args = list.toArray(new String[list.size()]);
        if (!sender.hasPermission(this.permission)) {
            this.msg(Lang.MSG_NOACCESS.toMsg());
            return;
        }
        if (this.senderMustBePlayer && !this.isPlayer()) {
            this.msg(Lang.MSG_PLAYERONLY.toMsg());
            return;
        }
        if (!(!this.senderMustBeInMarry || this.isPlayer() && this.isInClan())) {
            this.msg(Lang.MSG_INMARRYONLY.toMsg());
            return;
        }
        if (this.senderMustBeWithoutMarry && this.isPlayer() && this.isInClan()) {
            this.msg(Lang.MSG_NOMARRYONLY.toMsg());
            return;
        }
        this.execute();
    }

    public abstract void execute();

    public void msg(String s) {
        this.sender.sendMessage(s);
    }

    public void sendCorrectUsage() {
        this.msg(Lang.MSG_USAGE.toMsg().replace("%usage%", this.correctUsage));
    }

    public boolean isPlayer() {
        return this.player != null;
    }

    public boolean isInClan() {
        return this.marry != null;
    }

    public String buildStringFromArgs(int n, int n2) {
        String string;
        if (n2 < 0) {
            n2 = 0;
        }
        if (this.args.length > n + 1) {
            StringBuilder sb = new StringBuilder(this.args[n]);
            for (int i = n + 1; i < n2 + 1; ++i) {
                sb.append(" " + this.args[i]);
            }
            string = sb.toString();
        } else {
            string = this.args[n];
        }
        return string;
    }

    public String str(Integer obj) {
        return String.valueOf(obj);
    }

    public String getSenderName() {
        if (this.isPlayer()) {
            return this.player.getName();
        }
        return "Console";
    }
}

