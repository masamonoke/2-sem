package org.vsu.experimental;

import java.util.List;

public class RemoveCommand implements Command {
    @Override
    public void execute(CommandInput input) {

    }

    @Override
    public String getName() {
        return "rm";
    }

    private boolean rm(List<String> arguments, ) {
        if (argument == null) {
            System.out.println("Cannot execute command without argument");
            return;
        }
        pointer.getPointFolder().deleteUnit(argument);
    }
}
