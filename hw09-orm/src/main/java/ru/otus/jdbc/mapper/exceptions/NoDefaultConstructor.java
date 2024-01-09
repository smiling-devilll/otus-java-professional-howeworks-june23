package ru.otus.jdbc.mapper.exceptions;

public class NoDefaultConstructor extends RuntimeException {
    public NoDefaultConstructor(String className) {
        super("Class " + className + " has no default constructor");
    }
}
