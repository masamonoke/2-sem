package org.vsu.experimental;

import java.util.List;

public class CommandFactory {
    List<Command> commands;

    public void execute(String commandName, CommandInput input) {
        Command command = null;
        for (Command c : commands) {
            if (commandName.equals(c.getName())) {
                command = c;
            }
        }
        if (command != null) {
            command.execute(input);
        }
    }
}
