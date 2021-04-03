package com.csl.jjvm.cp;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author MaoLongLong
 */
@Slf4j
public class ClassPath {

    private Entry bootClasspath;

    private Entry extClasspath;

    private Entry userClasspath;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Props props;

    public ClassPath(String jrePath, String userPath) {
        props = new Props("jjvm.properties");
        parseBootAndExtClasspath(jrePath);
        parseUserClasspath(userPath);
    }

    public Class readClass(String className) throws IOException {
        className += ".class";

        Class clazz = bootClasspath.readClass(className);
        if (ObjectUtil.isNotNull(clazz)) {
            return clazz;
        }

        clazz = extClasspath.readClass(className);
        if (ObjectUtil.isNotNull(clazz)) {
            return clazz;
        }

        return userClasspath.readClass(className);
    }

    private void parseBootAndExtClasspath(String jrePath) {
        String jreDir = getJreDir(jrePath);
        LOGGER.info("configure jre to {}", jreDir);

        // jre/lib/*
        bootClasspath = new WildcardEntry(Paths.get(jreDir, "lib"));

        // jre/lib/ext/*
        extClasspath = new WildcardEntry(Paths.get(jreDir, "lib", "ext"));
    }

    private void parseUserClasspath(String userPath) {
        if (StrUtil.isBlank(userPath)) {
            userPath = ".";
        }

        LOGGER.info("user classpath {}", userPath);

        userClasspath = Entry.create(userPath);
    }

    private String getJreDir(String jrePath) {
        if (StrUtil.isNotBlank(jrePath) && Files.exists(Paths.get(jrePath))) {
            return jrePath;
        }

        String configJrePath = props.getStr("jjvm.jre-path");
        if (StrUtil.isNotBlank(configJrePath)) {
            return configJrePath;
        }

        String javaHome = System.getenv("JAVA_HOME");
        if (StrUtil.isNotBlank(javaHome)) {
            return javaHome;
        }

        throw new Error("can't find jre");
    }
}
