package fr.chatop.api.exceptionhandler;

public class GenericException extends RuntimeException {
    public GenericException(
            String message
    ) {
        super(message);
    }
}
