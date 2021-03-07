package org.vsu.experimental;

import org.vsu.Pointer;

public class TreeCommand implements Command {

    private Printable printable;

    public TreeCommand(Printable printable) {
        this.printable = printable;
    }

    @Override
    public void execute(CommandInput input) {
        printable.print(tree(input.getPointer()));
    }

    @Override
    public String getName() {
        return "tree";
    }

    //TODO make to return String
    private String tree(Pointer pointer) {
        return pointer.getPointFolder().drawTree();
    }
}
