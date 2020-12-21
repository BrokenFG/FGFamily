package ru.brfg.fgfamily.Commands.marry;

import java.util.ArrayList;
import java.util.List;
import ru.brfg.fgfamily.Marry;
import ru.brfg.fgfamily.utlis.BSubCommand;
import ru.brfg.fgfamily.utlis.Lang;
import ru.brfg.fgfamily.utlis.MarrySort;

public class CmdMarryList
extends BSubCommand {
    public CmdMarryList() {
        this.aliases.add("list");
        this.permission = "marry.list";
        this.senderMustBeInMarry = false;
        this.senderMustBePlayer = false;
        this.senderMustBeWithoutMarry = false;
    }

    @Override
    public void execute() {
        List<Marry> marryList = this.main.marryManager.getMarries(MarrySort.TIME);
        if (marryList.size() == 0) {
            this.msg(Lang.MSG_LIST_EMPTY.toMsg());
            return;
        }
        int page = 1;
        if (this.args.length == 1) {
            try {
                page = Math.max(Integer.parseInt(this.args[0]), 1);
            }
            catch (Exception exception) {
            }
        }
        ArrayList<String> results = new ArrayList<>();
        String result = "";
        int ind = 1;
        for (Marry marry : marryList) {
            List<String> partners = marry.getPartners();
            result = result + Lang.MSG_LIST_INFO.toString().replace("%ind%", String.valueOf(ind)).replace("%player%", partners.get(0)).replace("%player1%", partners.get(1)).replace("%time%", this.main.marryManager.getTime(marry.getTime())) + "\n";
            if (ind == 10) {
                ind = 1;
                results.add(result);
                result = "";
            }
            else {
                ++ind;
            }
        }
        if (ind != 1) {
            results.add(result);
        }
        page = Math.min(page, results.size());
        this.msg(Lang.MSG_LIST_PAGE.toString().replace("%page%", String.valueOf(page)).replace("%count%", String.valueOf(results.size())) + "\n" + results.get(page - 1));
    }
}

