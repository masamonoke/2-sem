package org.vsu.experimental;

import org.vsu.FolderTerminal;
import org.vsu.Pointer;

import java.util.List;

//TODO вычесть команду из списка params
public class CommandInput {

    private FolderTerminal folder;
    private List<String> params;
    private Pointer pointer;

    public Pointer getPointer() {
        return pointer;
    }

    public List<String> getParams() {
        return params;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public void setFolder(FolderTerminal folder) {
        this.folder = folder;
    }
}
