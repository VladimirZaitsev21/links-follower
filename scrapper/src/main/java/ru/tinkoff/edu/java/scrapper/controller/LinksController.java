package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/links")
public class LinksController {

    private Map<Long, ListLinksResponse> links = new HashMap<>();

    public LinksController() {
        links.put(123876L, new ListLinksResponse(
            Arrays.asList(
                new LinkResponse(1, "https://github.com/VladimirZaitsev21/some-repo"),
                new LinkResponse(2, "https://github.com/JohnDoe/navigator"),
                new LinkResponse(3, "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c")
            ), 3
        ));
        links.put(675438L, new ListLinksResponse(
            Arrays.asList(
                new LinkResponse(4, "https://stackoverflow.com/questions/5013327/using-multiple-values-httpstatus-in-responsestatus")
            ), 1
        ));
        links.put(574304L, new ListLinksResponse(
            Arrays.asList(
                new LinkResponse(5, "https://stackoverflow.com/questions/2469911/how-do-i-assert-my-exception-message-with-junit-test-annotation?rq=1")
            ), 1
        ));
    }

    @GetMapping
    public ListLinksResponse getAllLinks(@RequestParam long tgChatId) {
        return links.get(tgChatId);
    }

    @PostMapping
    public LinkResponse addLink(@RequestParam long tgChatId, @RequestBody AddLinkRequest request) {

        return null;
    }

    @DeleteMapping
    public LinkResponse deleteLink(@RequestParam long tgChatId, @RequestBody RemoveLinkRequest request) {
        return null;
    }
}
