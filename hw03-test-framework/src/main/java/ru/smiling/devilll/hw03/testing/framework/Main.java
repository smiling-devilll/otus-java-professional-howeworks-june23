package ru.smiling.devilll.hw03.testing.framework;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MyTestFramework.runTest(TestClass.class);
    }
}
