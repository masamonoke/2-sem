package org.vsu.experimental;

import org.vsu.Pointer;

import java.util.List;

public class MakeFolderCommand implements Command {

    @Override
    public void execute(CommandInput input) {
        mkdir(input.getParams(), input.getPointer());
    }

    @Override
    public String getName() {
        return "mkdir";
    }

    private void mkdir(List<String> arguments, Pointer pointer) {
        //String argument = arguments.size() == 1 ? arguments.get(0) : null;

        if (arguments == null) {
            return;
        }

        if (arguments.size() == 2) {
            pointer.getPointFolder().createFolder(arguments.get(1));
        }

    }
}
