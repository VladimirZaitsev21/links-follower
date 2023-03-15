package ru.tinkoff.edu.java.linkparser.parser.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public abstract class CommonUrlParser implements UrlParser {

    private static final Logger LOG = LogManager.getLogger(CommonUrlParser.class);
    protected final String processedAuthority;
    protected UrlParser nextParser;
    protected final Pattern pattern;

    protected CommonUrlParser(String processedAuthority, Pattern pattern) {
        this.processedAuthority = processedAuthority;
        this.pattern = pattern;
    }

    @Override
    public void setNext(UrlParser next) {
        this.nextParser = next;
    }

    @Override
    public UrlParserAnswer parse(String url) {
        if (url == null) return null;
        var parsedUrl = parseToUrl(url);
        if (parsedUrl == null) return null;
        var urlAuthority = parsedUrl.getAuthority();
        if (processedAuthority.equals(urlAuthority)) return extractPayloadFromUrl(parsedUrl);
        else return nextParser == null ? null : nextParser.parse(url);
    }

    protected abstract UrlParserAnswer extractPayloadFromUrl(URL parsedUrl);

    protected URL parseToUrl(String url) {
        URL parsedUrl = null;
        try {
            parsedUrl = new URL(url);
        } catch (MalformedURLException e) {
            LOG.error("Received URL [{}] is malformed!", url, e);
        }
        return parsedUrl;
    }
}
