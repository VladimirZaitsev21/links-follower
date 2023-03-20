package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    private final List<Long> tgIds = new ArrayList<>();

    @PostMapping("/{id}")
    public void registerChat(@PathVariable long id) {
        tgIds.add(id);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable long id) {
        tgIds.remove(id);
    }

}
