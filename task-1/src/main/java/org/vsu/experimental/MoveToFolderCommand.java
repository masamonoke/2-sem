package org.vsu.experimental;

import org.vsu.FolderTerminal;
import org.vsu.Pointer;
import org.vsu.Unit;

import java.util.List;

public class MoveToFolderCommand implements Command {
    @Override
    public void execute(CommandInput input) {
        cd(input.getParams(), input.getPointer());
    }

    @Override
    public String getName() {
        return "cd";
    }

    private void cd(List<String> arguments, Pointer pointer) {

        //String argument = arguments.size() == 1 ? arguments.get(0) : null;

        if (arguments.size() == 1) {
            pointer.setPointFolder(new FolderTerminal().getHome());

        } else if (arguments.get(1).equals("-")) {
            pointer.setPointFolder(pointer.getPrePointFolder());

        } else if (isAStringArgument(arguments.get(1)) && arguments.get(1).contains("/")) {
            String fullArgument = (FolderTerminal.HOME) + "/" + removeBrackets(arguments.get(1));
            FolderTerminal folder = FolderTerminal.getFolderFromPath(fullArgument, pointer.getPointFolder());

            if (folder == null) {
                System.out.println("Cannot find directory by path");
            } else {
                pointer.setPointFolder(FolderTerminal.getFolderFromPath(fullArgument, pointer.getPointFolder()));
            }

        }
        else {
            FolderTerminal tmp;
            tmp = pointer.getPointFolder().getFolder(arguments.get(1));
            if (tmp == null) {
                System.out.println("Cannot find folder");
                return;
            }
            pointer.setPointFolder(tmp);
        }

    }
}
