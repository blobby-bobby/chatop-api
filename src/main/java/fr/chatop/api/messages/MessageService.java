package fr.chatop.api.messages;

public interface MessageService {
    /**
     * Saves the message to the database
     * @param message Mapped to Dto message to be saved
     */
    void saveMessage(MessageDto message);
}
