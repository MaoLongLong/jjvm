package com.csl.jjvm.cp;

import com.csl.jjvm.util.Consts;
import com.csl.jjvm.util.ExtUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author MaoLongLong
 */
public interface Entry {

    /**
     * 读取类文件
     *
     * @param className 类全限定名 + ".class"
     * @return 类文件数据的字节数组和负责读取的具体 Entry
     * @throws IOException 读取 class 文件异常
     */
    Class readClass(String className) throws IOException;

    /**
     * 普通文件夹 -> DirEntry
     * dir1;dir2;dir3 -> CompositeEntry
     * dir/* -> WildcardEntry
     * xxx.(jar|zip) -> JarEntry
     *
     * @param path 类文件存储位置
     * @return 不同路径对应的 Entry
     */
    static Entry create(String path) {
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }
        if (path.endsWith(Consts.WILDCARD)) {
            return new WildcardEntry(path);
        }
        if (ExtUtil.isJar(path) || ExtUtil.isZip(path)) {
            return new JarEntry(path);
        }
        return new DirEntry(path);
    }
}
