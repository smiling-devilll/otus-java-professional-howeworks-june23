package ru.smiling.devilll.aop;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        TestLoggingImpl testLoggingClass = new TestLoggingImpl();
        MyTestLoggingInterface o = (MyTestLoggingInterface) Proxy.newProxyInstance(
                testLoggingClass.getClass().getClassLoader(),
                new Class<?>[]{MyTestLoggingInterface.class},
                new LoggingInvocationHandler(testLoggingClass));

        o.calculation(5);
        o.calculation(5, 10);
        o.calculation(5, 10, "123");
    }
}
