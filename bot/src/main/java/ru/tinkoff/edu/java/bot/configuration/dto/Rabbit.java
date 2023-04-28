package ru.tinkoff.edu.java.bot.configuration.dto;

public record Rabbit(
        String queue,
        String exchange,
        String user,
        String password,
        String host,
        int port,
        String virtualHost,
        String routingKey
) {
}
