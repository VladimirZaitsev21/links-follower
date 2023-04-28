package ru.tinkoff.edu.java.bot.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.common.model.LinkUpdate;

@Component
@RabbitListener(queues = "#{@rabbitQueue}")
public class ScrapperQueueListener {

    private final BotNotifier botNotifier;

    public ScrapperQueueListener(@Qualifier("rabbitMQBotNotifier") BotNotifier botNotifier) {
        this.botNotifier = botNotifier;
    }

    @RabbitHandler
    public void receive(LinkUpdate linkUpdate) {
        botNotifier.notify(linkUpdate);
    }
}
