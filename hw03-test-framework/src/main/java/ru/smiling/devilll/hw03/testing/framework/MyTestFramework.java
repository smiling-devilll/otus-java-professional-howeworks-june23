package ru.smiling.devilll.hw03.testing.framework;

import ru.smiling.devilll.hw03.testing.framework.annotations.AfterEach;
import ru.smiling.devilll.hw03.testing.framework.annotations.BeforeEach;
import ru.smiling.devilll.hw03.testing.framework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("java:S106")
public class MyTestFramework {

    public static <T> void runTest(Class<T> className) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        new MyTestFramework().run(className);
    }

    private <T> void run(Class<T> className) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int succeedTests = 0;
        int failedTests = 0;

        Method[] beforeEachMethods = findBeforeEach(className);
        Method[] afterEachMethods = findAfterEach(className);

        for (Method method : className.getDeclaredMethods()) {
            if (hasAnnotation(method, Test.class)) {
                T t = className.getDeclaredConstructor().newInstance();
                runMethodSet(t, beforeEachMethods);
                boolean testRes = runTest(t, method);
                if (testRes) { succeedTests++; }
                else { failedTests++; }
                runMethodSet(t, afterEachMethods);
            }
        }
        String testResultString = String.format("\n--------------\nTest results:\nSucceed tests: %d,\nFailed tests: %d", succeedTests, failedTests);
        System.out.println(testResultString);
    }

    private <T> void runMethodSet(T instance, Method[] beforeEachMethods) {
        for (Method method : beforeEachMethods) {
                try {
                    invokeMethod(method, instance);
                } catch (Exception ex) {
                    throw new RuntimeException(String.format("BeforeEach method %s failed", method.getName()), ex);
                }
        }
    }

    private <T> Method[] findBeforeEach(Class<T> className) {
        return Arrays.stream(className.getDeclaredMethods())
                .filter(m -> hasAnnotation(m, BeforeEach.class))
                .toArray(Method[]::new);
    }

    private <T> Method[] findAfterEach(Class<T> className) {
        return Arrays.stream(className.getDeclaredMethods())
                .filter(m -> hasAnnotation(m, AfterEach.class))
                .toArray(Method[]::new);
    }
    private boolean hasAnnotation(Method method, Class<? extends Annotation> clazz) {
        return method.isAnnotationPresent(clazz);
    }

    private <T> boolean runTest(T instance, Method method) {
        try {
            invokeMethod(method, instance);
            return true;
        } catch(Exception ex) {
            System.out.printf("Test method %s failed, cause %s%n", method.getName(), ex.toString());
            return false;
        }
    }

    private <T> void invokeMethod(Method method, T instance) throws IllegalAccessException {
        try {
            method.invoke(instance);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
