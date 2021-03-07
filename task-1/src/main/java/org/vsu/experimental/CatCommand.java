package org.vsu.experimental;

import org.vsu.FileTerminal;
import org.vsu.FolderTerminal;
import org.vsu.Pointer;

import java.util.List;
import java.util.Scanner;

public class CatCommand implements Command {

    Printable printable;

    public CatCommand(Printable printable) {
        this.printable = printable;
    }

    @Override
    public void execute(CommandInput input) {
        cat(input.getParams(), input.getPointer());
    }

    @Override
    public String getName() {
        return "cat";
    }

    private void cat(List<String> arguments, Pointer pointer) {
        if (arguments.size() == 1) {
            FileTerminal f = pointer.getPointFile();
            if (f == null) {
                //System.out.println("There is no file as argument");
                printable.print("There is no file as argument");
            } else {
                //return pointer.getPointFile().outputData();
                printable.print(pointer.getPointFile().outputData());
            }
            return;
        }

        //String[] arguments = argument.split(" ");
        if (arguments.size() == 2) {
            if (isAPath(arguments.get(1)) && isAStringArgument(arguments.get(1))) {
                //arguments[0] = FolderTerminal.HOME + "/" + removeBrackets(arguments[0]);
                arguments.set(1, FolderTerminal.HOME + "/" + removeBrackets(arguments.get(1)));
                pointer.setPointFile(FileTerminal.getFileFromPath(arguments.get(1), pointer.getPointFolder()));
            } else {
                pointer.setPointFile(pointer.getPointFolder().getFileFromFolder(arguments.get(1)));
            }

            FileTerminal f = pointer.getPointFile();
            if (f != null) {
                //f.outputData();
                printable.print(f.outputData());
            } else {
                //System.out.println("Cannot find file '" + removeBrackets(arguments.get(0)) + "'");
                printable.print("Cannot find file '" + removeBrackets(arguments.get(0)) + "'");
            }

        } else if (arguments.size() == 3) {

            if (isAPath(arguments.get(2)) && isAStringArgument(arguments.get(2))) {
                //arguments[1] = FolderTerminal.HOME + "/" + removeBrackets(arguments[1]);
                arguments.set(1, FolderTerminal.HOME + "/" + removeBrackets(arguments.get(2)));
                pointer.setPointFile(FileTerminal.getFileFromPath(arguments.get(2), pointer.getPointFolder()));
            } else {
                pointer.setPointFile(pointer.getPointFolder().getFileFromFolder(arguments.get(2)));
            }

            FileTerminal f = pointer.getPointFile();
            if (f == null) {
                //System.out.println("Cannot find file '" + arguments.get(1) + "'");
                printable.print("Cannot find file '" + arguments.get(2) + "'");
                return;
            }

            if (arguments.get(1).equals(">") && FileTerminal.isAType(arguments.get(2))) {

                System.out.print(">");
                Scanner scan = new Scanner(System.in);
                String line = scan.nextLine();
                f.appendToFile(line);

            } else if (arguments.get(1).equals(">>")) {
                System.out.print(">>");
                Scanner scan = new Scanner(System.in);
                String line = scan.nextLine();
                f.overwriteFile(line);
            }
        }
    }
}
