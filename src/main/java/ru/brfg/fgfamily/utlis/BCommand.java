package ru.brfg.fgfamily.utlis;

import java.util.ArrayList;
import java.util.List;
import ru.brfg.fgfamily.FGFamily;

public class BCommand {
    public FGFamily main;
    public List<BSubCommand> subcommands;

    public BCommand(FGFamily main) {
        this.main = main;
        this.subcommands = new ArrayList<BSubCommand>();
    }
}

