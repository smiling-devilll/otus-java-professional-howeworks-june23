package ru.otus.processor;

import ru.otus.model.Message;

public class ExceptionProcessor implements Processor {

    private final EvenDtChecker evenDtChecker;

    public ExceptionProcessor(EvenDtChecker evenDtChecker) {
        this.evenDtChecker = evenDtChecker;
    }

    @Override
    public Message process(Message message) {
        if (evenDtChecker.isEvenSecondNow()) {
            throw new EvenDtException("Second is even");
        }
        return message;
    }
}
