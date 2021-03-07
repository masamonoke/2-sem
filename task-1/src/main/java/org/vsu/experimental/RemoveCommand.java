package org.vsu.experimental;

import org.vsu.Pointer;

import java.util.List;

public class RemoveCommand implements Command {
    @Override
    public void execute(CommandInput input) {
        rm(input.getParams(), input.getPointer());
    }

    @Override
    public String getName() {
        return "rm";
    }

    private void rm(List<String> arguments, Pointer pointer) {

        String argument = arguments.size() == 1 ? arguments.get(0) : null;

        if (argument == null) {
            System.out.println("Cannot execute command without argument");
            return;
        }

        pointer.getPointFolder().deleteUnit(argument);
    }
}
