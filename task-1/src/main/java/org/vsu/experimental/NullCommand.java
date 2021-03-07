package org.vsu.experimental;

public class NullCommand implements Command {

    Printable printable;

    public NullCommand(Printable printable) {
        this.printable = printable;
    }

    @Override
    public void execute(CommandInput input) {
        printable.print("Cannot find command");
    }

    @Override
    public String getName() {
        return null;
    }
}
