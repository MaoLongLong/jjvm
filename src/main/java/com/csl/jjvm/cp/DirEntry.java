package com.csl.jjvm.cp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author MaoLongLong
 */
public class DirEntry implements Entry {

    private final Path path;

    public DirEntry(String path) {
        this.path = Paths.get(path);
    }

    @Override
    public Class readClass(String className) throws IOException {
        Path classFile = path.resolve(className);
        if (Files.exists(path)) {
            return new Class(Files.readAllBytes(classFile), this);
        }
        return null;
    }
}
