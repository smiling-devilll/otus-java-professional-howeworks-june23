package ru.smiling_devilll.hw03.testing.framework;

import ru.smiling_devilll.hw03.testing.framework.annotations.AfterEach;
import ru.smiling_devilll.hw03.testing.framework.annotations.BeforeEach;
import ru.smiling_devilll.hw03.testing.framework.annotations.Test;

@SuppressWarnings("all")
public class TestClass {

    @BeforeEach
    public void runBefore1() {
        System.out.println("run before 1");
    }

    @BeforeEach
    public void runBefore2() {
        System.out.println("run before 2");
    }

    @AfterEach
    public void runAfter1() {
        System.out.println("I'm after1");
    }

    @AfterEach
    public void runAfter2() {
        System.out.println("I'm after2");
    }

    @Test
    public void test1() {
        int res = 1 / 2;
        System.out.println(String.format("test1 res is %d", res));
    }
    @Test
    public void test2() {
        int res = 1 / 0;
        System.out.println(String.format("test1 res is %d", res));
    }
}
