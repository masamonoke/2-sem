package org.vsu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public interface Unit {

    String getName();

    String getPath();

    static List<String> getUnitStringsFromPath(String path) {
        String[] paths = path.split("/");
        List<String> foldersAndFiles = new ArrayList<>();
        IntStream.range(5, paths.length).forEach(i -> foldersAndFiles.add(paths[i]));
        return foldersAndFiles;
    }
}
