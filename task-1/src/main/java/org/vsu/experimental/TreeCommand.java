package org.vsu.experimental;

import org.vsu.Pointer;

public class TreeCommand implements Command {
    @Override
    public void execute(CommandInput input) {
        tree(input.getPointer());
    }

    @Override
    public String getName() {
        return "tree";
    }

    //TODO make to return String
    private void tree(Pointer pointer) {
        pointer.getPointFolder().drawTree();
    }
}
