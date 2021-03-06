package org.vsu.experimental;

import org.vsu.FolderTerminal;
import org.vsu.Pointer;

import java.util.List;

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
}
