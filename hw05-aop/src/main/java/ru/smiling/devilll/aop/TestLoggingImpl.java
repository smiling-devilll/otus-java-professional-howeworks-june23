package ru.smiling.devilll.aop;

public class TestLoggingImpl implements MyTestLoggingInterface {

    @Log
    public void calculation(int param1) {
        System.out.println("log with 1 param");
    }

    public void calculation(int param1, int param2) {
        System.out.println("log with 2 params");
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("log with 3 params");
    }
}
