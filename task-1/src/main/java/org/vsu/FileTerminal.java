package org.vsu;

import java.io.*;
import java.util.List;

public class FileTerminal implements Unit{
    private String name;
    private String path;
    private String parentPath;
    private String data;

    public FileTerminal(String name, String path) {
        this.name = name;
        this.path = path + "/" + name;
        this.parentPath = path;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            this.data = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToFile(String data) {
        this.data = this.data.concat("\n" + data);

        try {
            FileWriter fileWriter = new FileWriter(this.path, true);
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write("\n" + data);
                bufferedWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void overwriteFile(String data) {
        this.data = data;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error writing to a file");
        }
    }

    public String outputData() {
        //System.out.println(data);
        return data;
    }

    public static boolean isAType(String name) {
        return name.endsWith(".txt");
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    };

    public static FileTerminal getFileFromPath(String path, FolderTerminal f) {
        List<String> foldersAndFiles = Unit.getUnitStringsFromPath(path);

        List<Unit> currentWatchingFolder = f.getContents();
        for (String str : foldersAndFiles) {
            for (Unit unit : currentWatchingFolder) {
                if (unit instanceof FolderTerminal && unit.getName().equals(str)) {
                    currentWatchingFolder = ((FolderTerminal) unit).getContents();
                } else if (unit instanceof FileTerminal && unit.getName().equals(str)) {
                    return (FileTerminal) unit;
                }
            }
        }
        return null;
    }

    public static FileTerminal getFile(String name, List<Unit> units) {
        return (FileTerminal) units
                .stream()
                .filter(unit -> unit instanceof FileTerminal && unit.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
