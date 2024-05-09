package fr.ishtamar.starter.controllers;

import fr.ishtamar.starter.messages.MessageDto;
import fr.ishtamar.starter.messages.MessageService;
import fr.ishtamar.starter.messages.MessageServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody MessageDto message) {
        messageService.saveMessage(message);
        return "Message successfully sent";
    }
}
