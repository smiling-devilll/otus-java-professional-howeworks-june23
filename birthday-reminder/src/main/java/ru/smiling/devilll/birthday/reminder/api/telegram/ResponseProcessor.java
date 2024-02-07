package ru.smiling.devilll.birthday.reminder.api.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.smiling.devilll.birthday.reminder.api.telegram.model.ChatState;
import ru.smiling.devilll.birthday.reminder.api.telegram.model.KeyboardFactory;
import ru.smiling.devilll.birthday.reminder.api.telegram.model.TgButton;
import ru.smiling.devilll.birthday.reminder.domain.BirthdayService;
import ru.smiling.devilll.birthday.reminder.domain.reminder.ReminderService;
import ru.smiling.devilll.birthday.reminder.domain.UserService;
import ru.smiling.devilll.birthday.reminder.model.RemindPeriod;
import ru.smiling.devilll.birthday.reminder.model.SourceSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


//todo use separate command handlers
public class ResponseProcessor {

    private final Logger logger = LoggerFactory.getLogger(ResponseProcessor.class);
    private final BirthdayService birthdayService;
    private final UserService userService;
    private final ReminderService reminderService;
    private final MessageSender sender;


    private final Map<Long, ChatState> states;

    public ResponseProcessor(BirthdayService birthdayService, UserService userService, ReminderService reminderService, MessageSender sender) {
        this.birthdayService = birthdayService;
        this.userService = userService;
        this.reminderService = reminderService;
        this.sender = sender;
        this.states = new ConcurrentHashMap<>();
    }

    public void processStart(long chatId, String userName) {
        logger.info("User {} started bot", chatId);
        userService.createUser(userName, chatId, SourceSystem.TELEGRAM);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Привет! Я бот-склерозник. Выбери действие.");
        message.setReplyMarkup(KeyboardFactory.getBirthdayKeyboard());
        sendMessage(message);
    }

    public void replyToButtons(Update update) {
        var message = update.getMessage();
        var text = message.getText();
        var chatId = message.getChatId();
        var userId = message.getFrom().getId();
        logger.debug("received update {}", message);

        Optional<TgButton> commandButton = TgButton.findByName(text);
        if (commandButton.isPresent()) {
            handleCommand(commandButton.get(), userId, chatId);
        } else if (message.getText().equals("/start") || message.getText().equals("start")) {
            processStart(chatId, message.getFrom().getUserName());
        } else if (states.containsKey(message.getChatId())) {
            handleChatState(chatId, userId, text);
        } else {
            logger.error("Unknown command {}", text);
            var errorMsg = createErrorMessage(chatId);
            sendMessage(errorMsg);
        }
    }

    private void handleCommand(TgButton command, long userId, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (states.containsKey(chatId)) {
            clearState(chatId);
        }

        switch (command) {
            case GET_ALL -> handleGetAll(message, userId);

            case FIND_BIRTHDAY_BY_NAME -> {
                message.setText("Кого ищем? Дай фамилию");
                states.put(chatId, ChatState.AWAITING_PERSON_NAME_TO_FIND);
            }
            case ADD_BIRTHDAY -> {
                message.setText("Кого добавляем? Давай человека в формате \"Имя Фамилия ГГГГ-ММ-ДД\"");
                states.put(chatId, ChatState.AWAITING_PERSON_NAME_TO_ADD);
            }
            case SET_REMINDER -> {
                message.setText("Когда напомнить?");
                states.put(chatId, ChatState.AWAITING_REMINDER_PERIOD);
                message.setReplyMarkup(KeyboardFactory.getReminderKeyboard());
            }
        }
        sendMessage(message);
    }

    private void handleGetAll(SendMessage message, long userId) {
        try {
            var users = birthdayService.getAllForUser(userId, 0, 10);
            message.setText("Нашел " + users.size() + " человек:\n" + ReplyMapper.personsToString(users));
        } catch (Exception ex) {
            message.setText("Никого не нашел");
            logger.error("ex while getting users", ex);
        }
        message.setReplyMarkup(KeyboardFactory.getBirthdayKeyboard());
    }

    private void handleChatState(long chatId, long userId, String message) {
        var state = states.get(chatId);
        SendMessage reply = switch (state) {
            case AWAITING_PERSON_NAME_TO_FIND -> handleGetByName(userId, chatId, message);
            case AWAITING_PERSON_NAME_TO_ADD -> handleAddPerson(userId, chatId, message);
            case AWAITING_REMINDER_TIME -> handleSetReminderTime(userId, chatId, message);
            case AWAITING_REMINDER_PERIOD -> handleSetReminderPeriod(userId, chatId, message);
        };
        sendMessage(reply);
    }

    private SendMessage handleGetByName(long userId, long chatId, String message) {
        try {
            var mbPerson = birthdayService.findEntryByLastName(userId, message);
            var messageText =
                    mbPerson.map(ReplyMapper::personToString)
                            .orElse("Не нашел такого, давай еще раз или нажми другую команду");
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(messageText);

            if (mbPerson.isPresent()) {
                clearState(chatId);
                sendMessage.setReplyMarkup(KeyboardFactory.getBirthdayKeyboard());
            }
            return sendMessage;
        } catch (Exception ex) {
            logger.error("ex while getting users", ex);
            return createErrorMessage(chatId);
        }
    }

    private SendMessage handleAddPerson(long userId, long chatId, String message) {
        var reply = new SendMessage();
        try {
            var mbPerson = TgMessageParser.parsePerson(message);
            reply.setChatId(chatId);
            birthdayService.saveBirthdayEntry(mbPerson, userId);
            reply.setText("Добавил чувачка " + ReplyMapper.personToString(mbPerson));
            clearState(chatId);
        } catch (IllegalArgumentException ex) {
            reply.setText(ex.getMessage());
        }

        reply.setReplyMarkup(KeyboardFactory.getBirthdayKeyboard());
        return reply;
    }

    private SendMessage handleSetReminderTime(long userId, long chatId, String message) {
        var reply = new SendMessage();
        reply.setChatId(chatId);
        logger.info("User {} set remind time to {}", userId, message);
        try {
            var time = TgMessageParser.parseTime(message);
            reminderService.setRemindAt(userId, time);
            clearState(chatId);
            reply.setText("Установил напоминание на " + time);
        } catch (IllegalArgumentException ex) {
            reply.setText(ex.getMessage());
        } catch (Exception ex) {
            logger.error("хренота", ex);
            reply.setText("Произошло что-то не то, давай начнем все заново");
            reply.setReplyMarkup(KeyboardFactory.getBirthdayKeyboard());
        }
        return reply;
    }

    private SendMessage handleSetReminderPeriod(long userId, long chatId, String message) {
        var reply = new SendMessage();
        reply.setChatId(chatId);
        var remindPeriod = RemindPeriod.findByName(message);
        if (remindPeriod.isPresent()) {
            reminderService.setRemindPeriod(userId, remindPeriod.get());
            states.put(chatId, ChatState.AWAITING_REMINDER_TIME);
            reply.setText("Установил напоминание на " + remindPeriod.get().getName() + ". Во сколько напоминать? Давай время в формате \"Час:минута\"");
        } else {
            reply.setText("Не понял, во сколько напоминать?");
            reply.setReplyMarkup(KeyboardFactory.getReminderKeyboard());
        }
        return reply;
    }

    private SendMessage createErrorMessage(long chatId) {
        clearState(chatId);
        SendMessage msg = new SendMessage();
        msg.setText("Произошло что-то не то, давай начнем все заново");
        msg.setReplyMarkup(KeyboardFactory.getBirthdayKeyboard());
        msg.setChatId(chatId);
        return msg;
    }

    private void clearState(long chatId) {
        states.remove(chatId);
    }

    private void sendMessage(SendMessage message) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message to tg", e);
        }
    }
}
