package org.vsu.experimental;

public class EchoCommand implements Command {

    @Override
    public void execute(CommandInput input) {

    }

    @Override
    public String getName() {
        return "echo";
    }
}
