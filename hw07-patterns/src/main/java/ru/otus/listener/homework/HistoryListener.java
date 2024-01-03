package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> actions;

    public HistoryListener() {
        actions = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        var id = msg.getId();
        var clonedMessage = msg.toBuilder().deepClone();
        actions.put(id, clonedMessage);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(actions.get(id)).map(m -> m.toBuilder().deepClone());
    }
}
