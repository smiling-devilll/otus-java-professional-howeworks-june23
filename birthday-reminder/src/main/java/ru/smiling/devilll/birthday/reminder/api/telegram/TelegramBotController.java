package ru.smiling.devilll.birthday.reminder.api.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.smiling.devilll.birthday.reminder.conf.TelegramBotConf;
import ru.smiling.devilll.birthday.reminder.domain.BirthdayService;
import ru.smiling.devilll.birthday.reminder.domain.ReminderService;
import ru.smiling.devilll.birthday.reminder.domain.UserService;

import java.util.function.BiConsumer;

@Controller
public class TelegramBotController extends AbilityBot {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotController.class);

    private ResponseProcessor responseProcessor;
    private long creatorId;

    @Autowired
    public TelegramBotController(TelegramBotConf conf, BirthdayService birthdayService, UserService userService, ReminderService reminderService) {
        super(conf.getToken(), conf.getBotName());
        this.responseProcessor = new ResponseProcessor(birthdayService, userService, reminderService, sender);
        this.creatorId = conf.getCreatorId();
        logger.info("Init bot");
    }

    public Ability startBot() {
        return Ability
                .builder()
                .name("start")
                .info("hello user, I'm not working yet")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    logger.info("Processing start from {}", ctx.user().getUserName());
                    responseProcessor.processStart(ctx.chatId(), ctx.user().getUserName());
                })
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action =
                (abilityBot, upd) -> responseProcessor.replyToButtons(upd);
        return Reply.of(action, Flag.TEXT, upd -> true);
    }


    @Override
    public long creatorId() {
        return creatorId;
    }
}
