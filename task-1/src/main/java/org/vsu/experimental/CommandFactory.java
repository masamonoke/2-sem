package org.vsu.experimental;

import org.vsu.legasy.CommandsFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandFactory {
    List<Command> commands = new ArrayList<>();
    Printable printable;

    public CommandFactory(Printable printable) {
        this.printable = printable;
        commands.add(new EchoCommand(printable));
        commands.add(new ListCommand(printable));
        commands.add(new MakeFolderCommand());
        commands.add(new MoveToFolderCommand());
        commands.add(new RemoveCommand());
        commands.add(new TreeCommand(printable));
        commands.add(new CatCommand(printable));
    }

    public void execute(String commandName, CommandInput input) {
        /*Command command = null;
        for (Command c : commands) {
            if (commandName.equals(c.getName())) {
                command = c;
            }
        }*/
        Optional<Command> cmd = commands.stream().filter(c -> commandName.equals(c.getName())).findFirst();
        //cmd.ifPresent(value -> value.execute(input));
        cmd.ifPresentOrElse(value -> value.execute(input), () -> printable.print("Cannot find command"));
    }
}
