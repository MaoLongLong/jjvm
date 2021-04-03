package com.csl.jjvm.cp;

import cn.hutool.core.util.StrUtil;
import com.csl.jjvm.util.ExtUtil;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MaoLongLong
 */
public class WildcardEntry implements Entry {

    private final Path path;

    public WildcardEntry(String path) {
        // remove *
        this.path = Paths.get(StrUtil.sub(path, 0, -1));
    }

    public WildcardEntry(Path path) {
        this.path = path;
    }

    @Override
    public Class readClass(String className) throws IOException {

        List<Entry> entryList = new ArrayList<>();

        // try-with-resource
        try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
            for (Path file : dir) {
                if (Files.isDirectory(file)) {
                    continue;
                }
                if (ExtUtil.isJar(file.toString())) {
                    entryList.add(new JarEntry(file.toString()));
                }
            }
        }

        return new CompositeEntry(entryList).readClass(className);
    }
}
