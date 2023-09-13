package ru.smiling_devilll.hw03.testing.framework;

import ru.smiling_devilll.hw03.testing.framework.annotations.AfterEach;
import ru.smiling_devilll.hw03.testing.framework.annotations.BeforeEach;
import ru.smiling_devilll.hw03.testing.framework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("java:S106")
public class MyTestFramework {

    public static <T> void runTest(Class<T> className) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int succeedTests = 0;
        int failedTests = 0;

        for (Method method : className.getDeclaredMethods()) {
            if (hasAnnotation(method, Test.class)) {
                T t = className.getDeclaredConstructor().newInstance();
                runBeforeEach(t, className);
                boolean testRes = runTest(t, method);
                if (testRes) succeedTests++;
                else failedTests++;
                runAfterEach(t, className);
            }
        }
        String testResultString = String.format("\n--------------\nTest results:\nSucceed tests: %d,\nFailed tests: %d", succeedTests, failedTests);
        System.out.println(testResultString);
    }

    private static <T> void runBeforeEach(T instance, Class<T> className) {
        for (Method method : className.getDeclaredMethods()) {
            if (hasAnnotation(method, BeforeEach.class)) {
                try {
                    invokeMethod(method, instance);
                } catch (Exception ex) {
                    throw new RuntimeException(String.format("BeforeEach method %s failed", method.getName()), ex);
                }
            }
        }
    }

    private static boolean hasAnnotation(Method method, Class<? extends Annotation> clazz) {
        return method.isAnnotationPresent(clazz);
    }

    private static <T> void runAfterEach(T instance, Class<T> className) {
        for (Method method : className.getDeclaredMethods()) {
            if (hasAnnotation(method, AfterEach.class)) {
                try {
                    invokeMethod(method, instance);
                } catch(Exception ex) {
                    throw new RuntimeException(String.format("AfterEach method %s failed", method.getName()), ex);
                }
            }
        }
    }


    private static <T> boolean runTest(T instance, Method method) {
        try {
            invokeMethod(method, instance);
            return true;
        } catch(Exception ex) {
            System.out.println(String.format("Test method %s failed, cause %s", method.getName(), ex.toString()));
            return false;
        }
    }

    private static <T> void invokeMethod(Method method, T instance) throws IllegalAccessException {
        try {
            method.invoke(instance);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
