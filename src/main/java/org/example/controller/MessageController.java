package org.example.controller;

import org.example.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("/write")
    public String writeToFile() {
        return service.writeToFile();
    }

    @GetMapping("/jdbc-insert")
    public String insertMessage(@RequestParam(defaultValue = "Default message") String text) {
        return service.insertMessage(text);
    }

    @GetMapping("/jdbc-list")
    public List<String> listMessages() {
        return service.listMessages();
    }
}