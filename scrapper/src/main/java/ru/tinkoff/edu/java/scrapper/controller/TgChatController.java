package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    @PostMapping("/{id}")
    public LinkResponse registerChat(@PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public LinkResponse deleteChat(@PathVariable long id) {
        return null;
    }

}
