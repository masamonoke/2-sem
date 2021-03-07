package org.vsu.experimental;

import org.vsu.FileTerminal;
import org.vsu.FolderTerminal;
import org.vsu.Pointer;
import org.vsu.Unit;

import java.util.List;

public class EchoCommand implements Command {
    Printable printable;

    public EchoCommand(Printable printable) {
        this.printable = printable;
    }


    @Override
    public void execute(CommandInput input) {
        printable.print(echo(input.getParams(), input.getPointer()));
    }

    @Override
    public String getName() {
        return "echo";
    }

    //TODO make to return String
    private String echo(List<String> arguments, Pointer pointer) {


        if (! (arguments.get(0).endsWith("'") && arguments.get(0).startsWith("'")) ) {
            //System.out.println("Cannot define string to write");
            return "Cannot define string to write";
        }

        if (arguments.size() == 1) {
            System.out.println(arguments.get(0));

        } else if (arguments.size() == 3 && arguments.get(1).equals(">" ) && isAStringArgument(arguments.get(0))) {

            if (isAPath(arguments.get(2)) && isAStringArgument(arguments.get(2))) {
                arguments.set(0, removeBrackets(arguments.get(0)));
                arguments.set(2, removeBrackets(arguments.get(2)));
                arguments.set(2, (FolderTerminal.HOME) + "/" + arguments.get(2));
                pointer.setPointFile(FileTerminal.getFileFromPath(arguments.get(2), pointer.getPointFolder()));
                FileTerminal file = pointer.getPointFile();

                if (file == null) {
                    return "Cannot find file by path";
                } else {
                    pointer.getPointFile().overwriteFile(arguments.get(0));
                }

                return "";
            } else if (pointer.getPointFolder().isFolderContain(arguments.get(2))) {

                Unit u = pointer.getPointFolder().getByName(arguments.get(2));
                if (u instanceof FileTerminal) {
                    ((FileTerminal) u).overwriteFile(arguments.get(0));
                } else {
                    return "File referenced is not file";
                }

            } else {
                return "Cannot find file '" + arguments.get(0) + "'";
            }

            FileTerminal f = pointer.getPointFolder().getFileFromFolder(arguments.get(2));

            if (f != null) {
                pointer.setPointFile(f);
                pointer.getPointFile().appendToFile(arguments.get(0));
            } else {
                return "Cannot find file '" + arguments.get(2) + "'";
            }

        } else {
            return "Cannot define arguments";
        }
        return "";
    }
}
