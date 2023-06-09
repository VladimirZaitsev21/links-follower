package ru.tinkoff.edu.java.scrapper.service.impl.jooq;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.model.answer.GitHubUriParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.NotMatchedUriParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.StackOverflowUriParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.UriParsersChain;
import ru.tinkoff.edu.java.scrapper.domain.jooq.repository.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.model.TableLink;
import ru.tinkoff.edu.java.scrapper.service.api.BotNotifier;
import ru.tinkoff.edu.java.scrapper.service.api.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.model.Link;
import ru.tinkoff.edu.java.scrapper.webclient.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclient.api.StackOverflowClient;
import static ru.tinkoff.edu.java.common.model.LinkUpdateType.COMMON;
import static ru.tinkoff.edu.java.common.model.LinkUpdateType.GITHUB_ISSUES;
import static ru.tinkoff.edu.java.common.model.LinkUpdateType.STACKOVERFLOW_ANSWERS;

@Transactional
@RequiredArgsConstructor
public class JooqLinkUpdater implements LinkUpdater {

    public static final String OPEN_ISSUES_COUNT = "open_issues_count";
    public static final String ANSWER_COUNT = "answer_count";
    private final JooqLinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotNotifier botNotifier;
    private final UriParsersChain uriParsersChain;

    @Override
    public void update(long expiration) {
        var linksWithTgChatIds = linkRepository.findOld(expiration, System.currentTimeMillis());
        var links = linksWithTgChatIds.keySet();
        for (var link : links) {
            updateLink(convert(link), linksWithTgChatIds.get(link));
        }
    }

    private void updateLink(Link link, List<Long> tgChatIds) {
        var uriParserAnswer = uriParsersChain.doParse(link.link().toString());
        switch (uriParserAnswer) {
            case GitHubUriParserAnswer gitHubAnswer -> updateGitHubLink(link, tgChatIds, gitHubAnswer);
            case StackOverflowUriParserAnswer stackOverflowAnswer
                -> updateStackOverflowLink(link, tgChatIds, stackOverflowAnswer);
            case NotMatchedUriParserAnswer ignored -> { }
        }
    }

    private void updateGitHubLink(Link link, List<Long> tgChatIds, GitHubUriParserAnswer gitHubAnswer) {
        var gitHubResponse = gitHubClient.fetchRepo(gitHubAnswer.userAndRepo());
        var updateTime = gitHubResponse.pushedAt().toInstant().toEpochMilli();
        var openIssuesCount = link.updateInfo().get(OPEN_ISSUES_COUNT); //сценарий проверки issues
        link.updateInfo().put(OPEN_ISSUES_COUNT, gitHubResponse.openIssuesCount());

        if (openIssuesCount != null && gitHubResponse.openIssuesCount() != (int) openIssuesCount) {
            botNotifier.notify(link.id(), link.link(), GITHUB_ISSUES, tgChatIds);
            updateTime = System.currentTimeMillis();
        } else if (updateTime > link.updatedAt().toInstant().toEpochMilli()) {
            botNotifier.notify(link.id(), link.link(), COMMON, tgChatIds);
        }
        linkRepository
            .save(link.link().toString(), Timestamp.from(Instant.ofEpochMilli(updateTime)), link.updateInfo());
    }

    private void updateStackOverflowLink(
        Link link,
        List<Long> tgChatIds,
        StackOverflowUriParserAnswer stackOverflowAnswer
    ) {
        var stackOverflowResponse = stackOverflowClient.fetchQuestion(stackOverflowAnswer.id());
        var updateTime = stackOverflowResponse.lastActivityDate().toInstant().toEpochMilli();
        var answerCount = link.updateInfo().get(ANSWER_COUNT); //сценарий проверки количества ответов
        link.updateInfo().put(ANSWER_COUNT, stackOverflowResponse.answerCount());

        if (answerCount != null && stackOverflowResponse.answerCount() != (int) answerCount) {
            botNotifier.notify(link.id(), link.link(), STACKOVERFLOW_ANSWERS, tgChatIds);
            updateTime = System.currentTimeMillis();
        } else if (updateTime > link.updatedAt().toInstant().toEpochMilli()) {
            botNotifier.notify(link.id(), link.link(), COMMON, tgChatIds);
        }
        linkRepository
            .save(link.link().toString(), Timestamp.from(Instant.ofEpochMilli(updateTime)), link.updateInfo());
    }

    private Link convert(TableLink source) {
        return new Link(
                source.id(),
                URI.create(source.link()),
                OffsetDateTime.ofInstant(source.updatedAt().toInstant(), ZoneId.systemDefault()),
                source.updateInfo()
        );
    }
}
