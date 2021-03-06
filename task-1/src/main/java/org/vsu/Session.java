package org.vsu;

import java.util.Scanner;

public final class Session {
    private String user;
    private FolderTerminal rootFolder = new FolderTerminal();
    private Pointer pointer = new Pointer(rootFolder);

    public Session(String username) {
        this.user = username;
    }

    public void shellProcess() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            printShellCurrentPathAndUser();
            String input = scan.nextLine();
            interpretInput(input);
        }
        //scan.close();
    }

    private void printShellCurrentPathAndUser() {
        System.out.print(this.user + "@kek-computer:~" + pointer.getPointFolder().getPathForTerminalOutput() + "$ ");
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
