package ru.tinkoff.edu.java.scrapper.webclient.api;

import ru.tinkoff.edu.java.scrapper.model.request.LinkUpdateType;

import java.net.URI;
import java.util.List;

public interface BotClient {

    void sendUpdate(long id, URI url, LinkUpdateType updateType, List<Long> tgChatIds);
}
