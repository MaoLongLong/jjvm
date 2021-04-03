package com.csl.jjvm.cp;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

/**
 * @author MaoLongLong
 */
public class JarEntry implements Entry {

    private final String path;

    public JarEntry(String path) {
        this.path = path;
    }

    @Override
    public Class readClass(String className) throws IOException {
        ZipFile zipFile = new ZipFile(path);
        // try-with-resource
        try (InputStream is = ZipUtil.get(zipFile, className)) {
            if (ObjectUtil.isNotNull(is)) {
                return new Class(IoUtil.readBytes(is), this);
            }
        }
        return null;
    }
}
