package com.csl.jjvm.util;

/**
 * @author MaoLongLong
 */
public final class ExtUtil {

    private ExtUtil() {
    }

    public static boolean isJar(String filename) {
        return filename.endsWith(Consts.EXT_JAR_LOWER)
                || filename.endsWith(Consts.EXT_JAR_UPPER);
    }

    public static boolean isZip(String filename) {
        return filename.endsWith(Consts.EXT_ZIP_LOWER)
                || filename.endsWith(Consts.EXT_ZIP_UPPER);
    }
}
