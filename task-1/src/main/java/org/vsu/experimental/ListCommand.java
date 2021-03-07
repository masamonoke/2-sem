package org.vsu.experimental;

import org.vsu.Pointer;

import java.util.List;

public class ListCommand implements Command {

    Printable printable;

    public ListCommand(Printable printable) {
        this.printable = printable;
    }

    @Override
    public void execute(CommandInput input) {
        printable.print(ls(input.getParams(), input.getPointer()));
    }

    @Override
    public String getName() {
        return "ls";
    }

    //TODO make to return String
    private String ls(List<String> arguments, Pointer pointer) {
        if (arguments.size() > 1) {
            return "";
        }
        return pointer.getPointFolder().showContents();
    }
}
