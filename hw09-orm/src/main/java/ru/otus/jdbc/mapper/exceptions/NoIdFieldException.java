package ru.otus.jdbc.mapper.exceptions;

public class NoIdFieldException extends RuntimeException {
    public NoIdFieldException(String className) {
        super("Class " + className + " has no id field");
    }
}
