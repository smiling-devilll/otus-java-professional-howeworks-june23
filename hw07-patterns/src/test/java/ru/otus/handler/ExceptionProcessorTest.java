package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.EvenDtChecker;
import ru.otus.processor.EvenDtException;
import ru.otus.processor.ExceptionProcessor;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class ExceptionProcessorTest {

    @Test
    @DisplayName("Тестируем ошибки")
    void exceptionTest() {
        var message = new Message.Builder(1L).field10("field10").build();
        var alwaysThrow = new EvenDtChecker() {
            @Override
            public boolean isEvenSecondNow() {
                return true;
            }
        };
        var neverThrow = new EvenDtChecker() {
            @Override
            public boolean isEvenSecondNow() {
                return false;
            }
        };

        assertThatExceptionOfType(EvenDtException.class)
                .isThrownBy(() -> new ExceptionProcessor(alwaysThrow).process(message));

        assertThatNoException().isThrownBy(() -> new ExceptionProcessor(neverThrow).process(message));
    }
}
