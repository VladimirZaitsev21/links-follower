package ru.tinkoff.edu.java.scrapper.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient webClient(
            @Value("${web.url.base}") String baseUrl,
            @Value("${web.timeout.httpclient.ms}") int httpClientTimeout,
            @Value("${web.timeout.read.ms}") int readTimeout,
            @Value("${web.timeout.write.ms}") int writeTimeout
    ) {
        var httpClient = HttpClient
                .create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, httpClientTimeout)
                .doOnConnected(con -> {
                    con.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
                    con.addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS));
                });
        return WebClient.builder().baseUrl(baseUrl).clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
}
