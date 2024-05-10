package fr.chatop.api.services;

import fr.chatop.api.dto.MessageDto;

public interface MessageService {
    /**
     * Saves the message to the database
     * @param message Mapped to Dto message to be saved
     */
    void saveMessage(MessageDto message);
}
