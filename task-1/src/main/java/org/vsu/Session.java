package org.vsu;

import org.vsu.experimental.CommandFactory;
import org.vsu.experimental.CommandInput;
import org.vsu.experimental.Printable;
import org.vsu.legasy.CommandsFactory;

import java.util.Arrays;
import java.util.Scanner;

public final class Session {
    private final String USER;
    private final String COMPUTER_NAME;
    private FolderTerminal rootFolder = new FolderTerminal();
    private Pointer pointer = new Pointer(rootFolder);
    private final CommandFactory commandsFactory;
    private CommandInput commandInput = new CommandInput();

    public Session(String username, String computer_name, Printable printable) {
        USER = username;
        COMPUTER_NAME = computer_name;
        commandInput.setFolder(rootFolder);
        commandsFactory = new CommandFactory(printable);
    }

    public void shellProcess() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            printShellCurrentPathAndUser();
            String input = scan.nextLine();
            commandInput.setPointer(pointer);
            commandInput.setParams(Arrays.asList(input.split(" ")));
            commandsFactory.execute(commandInput.getParams().get(0), commandInput);
            //interpretInput(input);
        }
        //scan.close();
    }

    private void printShellCurrentPathAndUser() {
        System.out.print(USER + "@" + COMPUTER_NAME + ":~" + pointer.getPointFolder().getPathForTerminalOutput() + "$ ");
    }

    private void interpretInput(String input) {

        String[] commandParts = input.split(" ");
        String command = commandParts[0];

        String argument;
        argument = commandParts.length == 2 ? commandParts[1] : null;
        if (commandParts.length == 3) {
            argument = commandParts[1].concat(" ").concat(commandParts[2]);
        } else if (commandParts.length == 4) {
            argument = commandParts[1].concat(" " + commandParts[2]).concat(" " + commandParts[3]);
        }

        CommandsFactory commandsFactory = new CommandsFactory(rootFolder);
        commandsFactory.defineCommand(command, argument, this.pointer);
    }
}
