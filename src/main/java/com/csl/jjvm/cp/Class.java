package com.csl.jjvm.cp;

import cn.hutool.core.util.ObjectUtil;
import lombok.ToString;

/**
 * @author MaoLongLong
 */
@ToString(exclude = "data")
public class Class {

    private final byte[] data;

    private final Entry from;

    public Class(byte[] data, Entry from) {
        this.data = ObjectUtil.cloneByStream(data);
        this.from = from;
    }

    public byte[] getData() {
        return ObjectUtil.cloneByStream(data);
    }

    public Entry getFrom() {
        return from;
    }
}
