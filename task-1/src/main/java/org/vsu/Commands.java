package org.vsu;

import java.util.Scanner;

public class Commands {
    private final Pointer pointer;
    private final String argument;

    public Commands(Pointer pointer, String argument) {
        this.pointer = pointer;
        this.argument = argument;
    }

    public void ls() {
        if (argument != null) {
            return;
        }
        pointer.getPointFolder().showContents();
    }

    public void cat() {
        if (argument == null) {
            FileTerminal f = pointer.getPointFile();
            if (f == null) {
                System.out.println("There is no file as argument");
            } else {
                pointer.getPointFile().outputData();
            }
            return;
        }

        String[] arguments = argument.split(" ");
        if (arguments.length == 1) {
            if (isAPath(arguments[0]) && isAStringArgument(arguments[0])) {
                arguments[0] = FolderTerminal.HOME + "/" + removeBrackets(arguments[0]);
                pointer.setPointFile(FileTerminal.getFileFromPath(arguments[0], pointer.getPointFolder()));
            } else {
                pointer.setPointFile(pointer.getPointFolder().getFileFromFolder(argument));
            }

            FileTerminal f = pointer.getPointFile();
            if (f != null) {
                f.outputData();
            } else {
                System.out.println("Cannot find file '" + removeBrackets(argument) + "'");
            }

        } else if (arguments.length == 2) {

            if (isAPath(arguments[1]) && isAStringArgument(arguments[1])) {
                arguments[1] = FolderTerminal.HOME + "/" + removeBrackets(arguments[1]);
                pointer.setPointFile(FileTerminal.getFileFromPath(arguments[1], pointer.getPointFolder()));
            } else {
                pointer.setPointFile(pointer.getPointFolder().getFileFromFolder(arguments[1]));
            }

            FileTerminal f = pointer.getPointFile();
            if (f == null) {
                System.out.println("Cannot find file '" + arguments[1] + "'");
                return;
            }

            if (arguments[0].equals(">") && FileTerminal.isAType(arguments[1])) {

                System.out.print(">");
                Scanner scan = new Scanner(System.in);
                String line = scan.nextLine();
                f.appendToFile(line);

            } else if (arguments[0].equals(">>")) {
                System.out.print(">>");
                Scanner scan = new Scanner(System.in);
                String line = scan.nextLine();
                f.overwriteFile(line);
            }
        }
    }

    public void echo() {
        String[] arguments = argument.split(" ");

        if (! (arguments[0].endsWith("'") && arguments[0].startsWith("'")) ) {
            System.out.println("Cannot define string to write");
            return;
        }

        if (arguments.length == 1) {
            System.out.println(arguments[0]);

        } else if (arguments.length == 3 && arguments[1].equals(">" ) && isAStringArgument(arguments[0])) {

            if (isAPath(arguments[2]) && isAStringArgument(arguments[2])) {
                arguments[0] = removeBrackets(arguments[0]);
                arguments[2] = removeBrackets(arguments[2]);
                arguments[2] = (FolderTerminal.HOME) + "/" + arguments[2];
                pointer.setPointFile(FileTerminal.getFileFromPath(arguments[2], pointer.getPointFolder()));
                FileTerminal file = pointer.getPointFile();

                if (file == null) {
                    System.out.println("Cannot find file by path");
                    return;
                } else {
                    pointer.getPointFile().overwriteFile(arguments[0]);
                }

                return;
            } else if (pointer.getPointFolder().isFolderContain(arguments[2])) {

                Unit u = pointer.getPointFolder().getByName(arguments[2]);
                if (u instanceof FileTerminal) {
                    ((FileTerminal) u).overwriteFile(arguments[0]);
                } else {
                    System.out.println("File referenced is not file");
                }

            } else {
                System.out.println("Cannot find file '" + arguments[0] + "'");
                return;
            }

            FileTerminal f = pointer.getPointFolder().getFileFromFolder(arguments[2]);

            if (f != null) {
                pointer.setPointFile(f);
                pointer.getPointFile().appendToFile(arguments[0]);
            } else {
                System.out.println("Cannot find file '" + arguments[2] + "'");
            }

        } else {
            System.out.println("Cannot define arguments");
        }
    }

    private boolean isAStringArgument(String str) {
        return str.startsWith("'") && str.endsWith("'");
    }

    private boolean isAPath(String path) {
        return path.contains("/");
    }

    private String removeBrackets(String str) {
        if (str.length() > 3) {
            return str.substring(1, str.length() - 1);
        } else {
            return str;
        }
    }

    public void rm() {
        if (argument == null) {
            System.out.println("Cannot execute command without argument");
            return;
        }
        pointer.getPointFolder().deleteUnit(argument);
    }

    public void cd(Unit unit) {
        if (argument == null) {
            pointer.setPointFolder(((FolderTerminal)unit).getHome());
        } else if (argument.equals("-")) {
            pointer.setPointFolder(pointer.getPrePointFolder());

        } else if (isAStringArgument(argument) && argument.contains("/")) {
            String fullArgument = (FolderTerminal.HOME) + "/" + removeBrackets(argument);
            FolderTerminal folder = FolderTerminal.getFolderFromPath(fullArgument, pointer.getPointFolder());
            //TODO
            if (folder == null) {
                System.out.println("Cannot find directory by path");
            } else {
                pointer.setPointFolder(FolderTerminal.getFolderFromPath(fullArgument, pointer.getPointFolder()));
            }
        }
        else {
            FolderTerminal tmp;
            tmp = pointer.getPointFolder().getFolder(argument);
            if (tmp == null) {
                System.out.println("Cannot find folder");
                return;
            }
            pointer.setPointFolder(tmp);
        }
    }

    public void mkdir() {
        pointer.getPointFolder().createFolder(argument);
    }

    public void tree() {
        pointer.getPointFolder().drawTree();
    }
}
