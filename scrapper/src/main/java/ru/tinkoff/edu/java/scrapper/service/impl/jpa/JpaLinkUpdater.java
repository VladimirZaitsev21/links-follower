package ru.tinkoff.edu.java.scrapper.service.impl.jpa;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.model.answer.GitHubUriParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.NotMatchedUriParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.StackOverflowUriParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.UriParsersChain;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.model.jpa.JpaChat;
import ru.tinkoff.edu.java.scrapper.domain.model.jpa.JpaLink;
import ru.tinkoff.edu.java.scrapper.service.api.BotNotifier;
import ru.tinkoff.edu.java.scrapper.service.api.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.webclient.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.api.StackOverflowClient;
import static ru.tinkoff.edu.java.common.model.LinkUpdateType.COMMON;
import static ru.tinkoff.edu.java.common.model.LinkUpdateType.GITHUB_ISSUES;
import static ru.tinkoff.edu.java.common.model.LinkUpdateType.STACKOVERFLOW_ANSWERS;

@Transactional
@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {

    public static final String OPEN_ISSUES_COUNT = "open_issues_count";
    public static final String ANSWER_COUNT = "answer_count";
    private final JpaLinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotNotifier botNotifier;
    private final UriParsersChain uriParsersChain;

    @Override
    public void update(long expiration) {
        var timeBorder = System.currentTimeMillis() - expiration;
        var oldLinks = linkRepository.findByUpdatedAtLessThan(new Timestamp(timeBorder));
        oldLinks.forEach(this::updateLink);
    }

    private void updateLink(JpaLink link) {
        var uriParserAnswer = uriParsersChain.doParse(link.getLink());
        switch (uriParserAnswer) {
            case GitHubUriParserAnswer gitHubAnswer -> updateGitHubLink(link, gitHubAnswer);
            case StackOverflowUriParserAnswer stackOverflowAnswer -> updateStackOverflowLink(link, stackOverflowAnswer);
            case NotMatchedUriParserAnswer ignored -> { }
        }
    }

    private void updateGitHubLink(JpaLink link, GitHubUriParserAnswer gitHubAnswer) {
        var gitHubResponse = gitHubClient.fetchRepo(gitHubAnswer.userAndRepo());
        var updateTime = gitHubResponse.pushedAt().toInstant().toEpochMilli();
        var openIssuesCount = link.getUpdateInfo().get(OPEN_ISSUES_COUNT);
        link.getUpdateInfo().put(OPEN_ISSUES_COUNT, gitHubResponse.openIssuesCount());

        if (openIssuesCount != null && gitHubResponse.openIssuesCount() != (int) openIssuesCount) {
            updateTime = System.currentTimeMillis();
            botNotifier.notify(
                    link.getId(),
                    URI.create(link.getLink()),
                    GITHUB_ISSUES,
                    link.getTrackingJpaChats().stream().map(JpaChat::getTgChatId).toList()
            );
        } else if (updateTime > link.getUpdatedAt().toInstant().toEpochMilli()) {
            updateTime = System.currentTimeMillis();
            botNotifier.notify(
                    link.getId(),
                    URI.create(link.getLink()),
                    COMMON,
                    link.getTrackingJpaChats().stream().map(JpaChat::getTgChatId).toList()
            );
        }
        link.setUpdatedAt(Timestamp.from(Instant.ofEpochMilli(updateTime)));
        linkRepository.saveAndFlush(link);
    }

    private void updateStackOverflowLink(JpaLink link, StackOverflowUriParserAnswer stackOverflowAnswer) {
        var stackOverflowResponse = stackOverflowClient.fetchQuestion(stackOverflowAnswer.id());
        var updateTime = stackOverflowResponse.lastActivityDate().toInstant().toEpochMilli();
        var answerCount = link.getUpdateInfo().get(ANSWER_COUNT);
        link.getUpdateInfo().put(ANSWER_COUNT, stackOverflowResponse.answerCount());

        if (answerCount != null && stackOverflowResponse.answerCount() != (int) answerCount) {
            botNotifier.notify(
                    link.getId(),
                    URI.create(link.getLink()),
                    STACKOVERFLOW_ANSWERS,
                    link.getTrackingJpaChats().stream().map(JpaChat::getTgChatId).toList()
            );
        } else if (updateTime > link.getUpdatedAt().toInstant().toEpochMilli()) {
            botNotifier.notify(
                    link.getId(),
                    URI.create(link.getLink()),
                    COMMON,
                    link.getTrackingJpaChats().stream().map(JpaChat::getTgChatId).toList()
            );
        }
        link.setUpdatedAt(Timestamp.from(Instant.ofEpochMilli(updateTime)));
        linkRepository.save(link);
    }
}
