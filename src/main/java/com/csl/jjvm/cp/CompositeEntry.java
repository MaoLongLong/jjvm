package com.csl.jjvm.cp;

import cn.hutool.core.util.ObjectUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MaoLongLong
 */
public class CompositeEntry implements Entry {

    private final List<Entry> entryList;

    public CompositeEntry(String pattern) {
        entryList = new ArrayList<>();
        for (String path : pattern.split(File.pathSeparator)) {
            entryList.add(Entry.create(path));
        }
    }

    public CompositeEntry(List<Entry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public Class readClass(String className) throws IOException {
        for (Entry entry : entryList) {
            Class clazz = entry.readClass(className);
            if (ObjectUtil.isNotNull(clazz)) {
                return new Class(clazz.getData(), this);
            }
        }
        return null;
    }
}
