package org.vsu.experimental;

import org.vsu.Pointer;

import java.util.List;

public class ListCommand implements Command {

    @Override
    public void execute(CommandInput input) {
        ls(input.getParams(), input.getPointer());
    }

    @Override
    public String getName() {
        return "ls";
    }

    private void ls(List<String> arguments, Pointer pointer) {
        if (arguments != null) {
            return;
        }
        pointer.getPointFolder().showContents();
    }
}
