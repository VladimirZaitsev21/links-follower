package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegram.TelegramBot;
import ru.tinkoff.edu.java.common.model.LinkUpdate;

@Service
public class RabbitMQBotNotifier implements BotNotifier {

    private final TelegramBot bot;

    public RabbitMQBotNotifier(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void notify(LinkUpdate linkUpdate) {
        bot.notifyAboutLinkUpdate(linkUpdate.url().toString(), linkUpdate.updateType(), linkUpdate.tgChatIds());
    }
}
