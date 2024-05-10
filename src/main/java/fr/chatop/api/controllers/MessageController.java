package fr.chatop.api.controllers;

import fr.chatop.api.messages.MessageService;
import fr.chatop.api.messages.MessageDto;
import fr.chatop.api.messages.MessageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "sends the message",responses={
            @ApiResponse(responseCode="200", description = "Message successfully sent"),
            @ApiResponse(responseCode="404", description = "Access unauthorized"),
            @ApiResponse(responseCode="404", description = "Couldn't find user or rental"),
    })
    @PostMapping("/messages")
    public String sendMessage(@RequestBody MessageDto message) {
        messageService.saveMessage(message);
        return "Message successfully sent";
    }
}
