package com.csl.jjvm;

import com.csl.jjvm.cp.Class;
import com.csl.jjvm.cp.ClassPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author MaoLongLong
 */
@Slf4j
class JJvmTest {

    @Test
    void testJJvm() {
        assertDoesNotThrow(() -> {
            ClassPath cp = new ClassPath(null, null);
            Class clazz = cp.readClass("java/lang/String");
            LOGGER.info("class: {}", clazz);
            LOGGER.info("len: {}", clazz.getData().length);
        });
    }
}
