package org.vsu.experimental;

public interface Command {

    void execute(CommandInput input);

    String getName();

}
