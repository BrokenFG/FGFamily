package ru.brfg.fgfamily.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.brfg.fgfamily.Commands.marry.CmdMarryAccept;
import ru.brfg.fgfamily.Commands.marry.CmdMarryDecline;
import ru.brfg.fgfamily.Commands.marry.CmdMarryDivorce;
import ru.brfg.fgfamily.Commands.marry.CmdMarryInfo;
import ru.brfg.fgfamily.Commands.marry.CmdMarryInvite;
import ru.brfg.fgfamily.Commands.marry.CmdMarryList;
import ru.brfg.fgfamily.Commands.marry.CmdMarryPvp;
import ru.brfg.fgfamily.Commands.marry.CmdMarrySeen;
import ru.brfg.fgfamily.Commands.marry.CmdMarryTeleport;
import ru.brfg.fgfamily.FGFamily;
import ru.brfg.fgfamily.utlis.BCommand;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;

public class CmdMarry extends BCommand implements CommandExecutor {
    public CmdMarry(FGFamily main) {
        super(main);
        this.subcommands.add(new CmdMarryAccept());
        this.subcommands.add(new CmdMarryDecline());
        this.subcommands.add(new CmdMarryDivorce());
        this.subcommands.add(new CmdMarryInfo());
        this.subcommands.add(new CmdMarryInvite());
        this.subcommands.add(new CmdMarryList());
        this.subcommands.add(new CmdMarryPvp());
        this.subcommands.add(new CmdMarrySeen());
        this.subcommands.add(new CmdMarryTeleport());
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] array) {
        if (array.length < 1) {
            commandSender.sendMessage(Lang.MSG_HELP.toMsg());
            return true;
        }
        for (BSubCommand bSubCommand : this.subcommands) {
            if (!bSubCommand.aliases.contains(array[0])) continue;
            bSubCommand.execute(commandSender, array);
            return true;
        }
        commandSender.sendMessage(Lang.MSG_HELP.toMsg());
        return true;
    }
}

