package ru.tinkoff.edu.java.common.model;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

public record LinkUpdate(long id, URI url, LinkUpdateType updateType, List<Long> tgChatIds) implements Serializable {
}