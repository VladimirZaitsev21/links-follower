package ru.tinkoff.edu.java.scrapper.webclient.impl;

import java.net.URI;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.common.model.LinkUpdate;
import ru.tinkoff.edu.java.common.model.LinkUpdateType;
import ru.tinkoff.edu.java.scrapper.webclient.api.BotClient;

public class HttpBotClient implements BotClient {

    private static final String BASE_URL = "https://localhost:9000/updates";

    private final String baseUrl;
    private final WebClient webClient;

    public HttpBotClient(WebClient webClient) {
        this.webClient = webClient;
        baseUrl = BASE_URL;
    }

    public HttpBotClient(String baseUrl, WebClient webClient) {
        this.baseUrl = baseUrl;
        this.webClient = webClient;
    }

    @Override
    public void sendUpdate(long id, URI url, LinkUpdateType updateType, List<Long> tgChatIds) {
        webClient.post()
                .uri(baseUrl)
                .bodyValue(new LinkUpdate(id, url, updateType, tgChatIds))
                .retrieve().bodyToMono(Void.class).subscribe();
    }
}
