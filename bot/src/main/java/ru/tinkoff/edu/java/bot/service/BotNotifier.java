package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.common.model.LinkUpdate;

public interface BotNotifier {

    void notify(LinkUpdate linkUpdate);
}
