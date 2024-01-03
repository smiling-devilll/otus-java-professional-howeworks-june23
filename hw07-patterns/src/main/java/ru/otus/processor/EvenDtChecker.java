package ru.otus.processor;

public interface EvenDtChecker {
    default boolean isEvenSecondNow() {
        var dt = java.time.LocalDateTime.now();
        return dt.getSecond() % 2 == 0;
    }
}
