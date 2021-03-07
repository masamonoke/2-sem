package org.vsu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FolderTerminal implements Unit{
    private final String ANSI_PURPLE = "\u001B[35m";
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_RED = "\u001B[31m";
    public static final String HOME = "./src/main/resources/home";

    private String name;
    private String parentPath;
    private String path;
    private List<Unit> contents = new ArrayList<>();
    private List<Unit> parentContents;

    public FolderTerminal() {
        this.parentPath = "";
        this.path = HOME;
        this.name = "home";
        generateHome();
    }

    private FolderTerminal(String name, String path, List<Unit> parentContents) {
        this.parentPath = path;
        this.path = path + "/" + name;
        this.name = name;
        this.parentContents = parentContents;
        generateContents(this.contents, this.path);
    }
    private void generateHome() {
        generateContents(this.contents, HOME);
    }

    public FolderTerminal getHome() {
        return new FolderTerminal();
    }

    private void generateContents(List<Unit> contents, String path) {

        if (this.contents == null) {
            return;
        }

        File f = new File(path);
        for (String unit : Objects.requireNonNull(f.list())) {
            if (FolderTerminal.isAType(unit)) {
                contents.add(new FolderTerminal(unit, this.path, this.contents));
            } else if (FileTerminal.isAType(unit)) {
                contents.add(new FileTerminal(unit, this.path));
            }
        }
    }

    public List<Unit> getContents() {
        return this.contents;
    }

    public void createFolder(String name) {

        if (name == null) {
            System.out.println("Can't create folder without name");
            return;
        }

        if (name.contains("/")) {
            System.out.println("Cannot create folder '" + name + "', because it contains '/' symbol");
            return;
        }

        File f = new File(this.path + "//" + name);
        if (f.mkdir()) {
            System.out.println("A folder '" + name + "' has been created");
            this.contents.add(new FolderTerminal(name, this.path, this.parentContents));
        } else {
            System.out.println("There is already folder names '" + name + "'");
        }
    }

    public String drawTree() {
        StringBuilder stringBuilder = new StringBuilder();
        tree(-1, stringBuilder);
        return stringBuilder.toString();
    }
    private void tree(int indent, StringBuilder stringBuilder) {
        if (indent == 0) {
            //System.out.print("├");
            stringBuilder.append("├");
        } else if (indent > 0) {
            //System.out.print("│");
            stringBuilder.append("│");
            //printSpace(indent);
            stringBuilder.append(printSpace(indent));
            //System.out.print("├");
            stringBuilder.append("├");
        }
        //System.out.print(ANSI_BLUE + this.name + ANSI_RESET);
        stringBuilder.append(ANSI_BLUE).append(this.name).append(ANSI_RESET);
        //System.out.println();
        stringBuilder.append("\n");
        indent++;
        for (Unit unit : this.contents) {
            if (unit instanceof FolderTerminal) {
                ((FolderTerminal) unit).tree(indent, stringBuilder);
            } else if (unit instanceof FileTerminal) {
                //System.out.print("│");
                stringBuilder.append("│");
                //printSpace(indent);
                stringBuilder.append(printSpace(indent));
                //System.out.println("├" + ANSI_RED + unit.getName() + ANSI_RESET);
                stringBuilder.append("├" + ANSI_RED).append(unit.getName()).append(ANSI_RESET);
            }
        }
    }
    private StringBuilder printSpace(int indent) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            //System.out.print(" ");
            stringBuilder.append(" ");
        }
        return stringBuilder;
    }

    public String showContents() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.contents != null) {
            for (Unit unit : contents) {
                if (unit instanceof FileTerminal) {
                    //System.out.print(ANSI_RED + unit.getName() + " " + ANSI_RESET);
                    stringBuilder.append(ANSI_RED).append(unit.getName()).append(" ").append(ANSI_RESET);
                } else if (unit instanceof  FolderTerminal) {
                    //System.out.print(ANSI_BLUE + unit.getName() + " " + ANSI_RESET);
                    stringBuilder.append(ANSI_BLUE).append(unit.getName()).append(" ").append(ANSI_RESET);
                }
            }
            //System.out.println();
            return stringBuilder.toString();
        }
        return "";
    }

    public String getPathForTerminalOutput() {
        String[] directories = path.split("/");
        boolean canConcat = false;
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < directories.length; i++) {
            if (directories[i].equals("home")) {
                canConcat = true;
                continue;
            }
            if (canConcat) {
                path.append(directories[i]).append("/");
            }
        }
        return path.toString();
    }

    public static boolean isAType(String name) {
        return !name.contains(".");
    }

    public String getName() {
        return this.name;
    }

    public FolderTerminal getFolder(String name) {
        if (!name.contains("/")) {
            return (FolderTerminal)
                    this.contents.stream()
                            .filter(unit -> name.equals(unit.getName()) && unit instanceof FolderTerminal)
                            .findFirst().orElse(null);
        } else {
            String[] directories = name.split("/");
            FolderTerminal folderTerminal = new FolderTerminal();
            Unit pointer = new FolderTerminal();
            for (String strDirectory : directories) {

                for (Unit unit : folderTerminal.getContents()) {
                    if (unit.getName().equals(strDirectory)) {
                        pointer = unit;
                        break;
                    }
                }
                folderTerminal = (FolderTerminal) pointer;
            }
            return folderTerminal;
        }
    }

    public String getPath() {
        return this.path;
    }

    public void deleteUnit(String name) {
        boolean isThereUnit = false;

        Unit unitToRemove = null;
        for (Unit unit : this.contents) {
            if (unit.getName().equals(name)) {
                isThereUnit = true;
                unitToRemove = unit;

                if (unit instanceof FolderTerminal) {
                    try {
                        deleteDirectory(unit.getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                else if (unit instanceof FileTerminal) {
                    File f = new File(unit.getPath());
                    if (!f.delete()) {
                        System.out.println("Cannot delete " + name + " file");
                        return;
                    }
                    break;
                }
            }
        }

        if (isThereUnit) {
            this.contents.remove(unitToRemove);
        } else {
            System.out.println("Cannot delete '" + name + "' file");
        }
    }
    private void deleteDirectory(String dir) throws IOException {
        Path p = Paths.get(dir);
        try (Stream<Path> walk = Files.walk(p)) {
            walk.sorted(Comparator.reverseOrder()).forEach(FolderTerminal::deleteDirectoryExtract);
        }
    }
    private static void deleteDirectoryExtract(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    public FileTerminal getFileFromFolder(String name) {
        return FileTerminal.getFile(name, this.contents);
    }

    public static FolderTerminal getFolderFromPath(String path, FolderTerminal root) {
        List<String> foldersAndFiles = Unit.getUnitStringsFromPath(path);

        List<Unit> currentWatchingFolder = root.getContents();
        Unit currentUnit = null;
        for (String str : foldersAndFiles) {
            for (Unit unit : currentWatchingFolder) {
                if (unit instanceof FolderTerminal && unit.getName().equals(str)) {
                    currentWatchingFolder = ((FolderTerminal) unit).getContents();
                    currentUnit = unit;
                }
            }
        }
        return (FolderTerminal) currentUnit;

    }

    public boolean isFolderContain(String name) {
        for (Unit unit : this.contents) {
            if (unit.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Unit getByName(String name) {
        for (Unit unit : this.contents) {
            if (unit.getName().equals(name)) {
                return unit;
            }
        }
        return null;
    }

}
