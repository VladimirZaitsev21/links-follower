package ru.tinkoff.edu.java.bot.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.model.LinkUpdate;

@RestController
@RequestMapping("/updates")
public class UpdatesController {

    @PostMapping
    public void sendUpdate(@RequestBody LinkUpdate update) {

    }
}
