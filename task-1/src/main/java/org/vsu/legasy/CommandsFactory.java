package org.vsu.legasy;

import org.vsu.FolderTerminal;
import org.vsu.Pointer;
import org.vsu.Unit;
import org.vsu.legasy.Commands;

public class CommandsFactory {

    private final Unit ROOT;

    public CommandsFactory(Unit root) {
        this.ROOT = root;
    }

    public void defineCommand(String command, String argument, Pointer pointer) {
        if (ROOT.getClass() == FolderTerminal.class) {
            Commands commands = new Commands(pointer, argument);
            switch (command) {
                case "ls" -> commands.ls();
                case "cd" -> commands.cd(ROOT);
                case "mkdir" -> commands.mkdir();
                case "rm" -> commands.rm();
                case "tree" -> commands.tree();
                case "cat" -> commands.cat();
                case "echo" -> commands.echo();
                case "" -> {}
                default -> System.out.println("Cannot find command");
            }
        }
    }
}
