package fr.chatop.api.services.impl;

import fr.chatop.api.dto.MessageDto;
import fr.chatop.api.mappers.MessageMapper;
import fr.chatop.api.repositories.MessageRepository;
import fr.chatop.api.repositories.RentalRepository;
import fr.chatop.api.repositories.UserInfoRepository;
import fr.chatop.api.services.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserInfoRepository userInfoRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            RentalRepository rentalRepository,
            UserInfoRepository userInfoRepository,
            MessageMapper messageMapper
    ) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.userInfoRepository = userInfoRepository;
        this.rentalRepository = rentalRepository;
    }

    public void saveMessage(MessageDto message) {
        message.setCreated_at(LocalDateTime.now());
        message.setUpdated_at(LocalDateTime.now());
        userInfoRepository.findById(message.getUser_id());
        rentalRepository.findById(message.getRental_id());
        messageRepository.save(messageMapper.toEntity(message));
    }

}
