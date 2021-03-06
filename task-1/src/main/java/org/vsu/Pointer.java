package org.vsu;

public class Pointer {
    private FileTerminal pointFile;
    private FolderTerminal pointFolder;
    private FolderTerminal prePointFolder;

    public Pointer (FolderTerminal root) {
        this.pointFolder = root;
        this.prePointFolder = root;
    }

    public void setPointFolder(FolderTerminal folder) {
        this.prePointFolder = this.pointFolder;
        this.pointFolder = folder;
    }

    public FolderTerminal getPointFolder() {
        return pointFolder;
    }

    public FolderTerminal getPrePointFolder() {
        return prePointFolder;
    }

    public FileTerminal getPointFile() {
        return this.pointFile;
    }

    public void setPointFile(FileTerminal file) {
        this.pointFile = file;
    }
}
