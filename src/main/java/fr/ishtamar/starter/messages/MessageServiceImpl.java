package fr.ishtamar.starter.messages;

import fr.ishtamar.starter.rentals.RentalRepository;
import fr.ishtamar.starter.user.UserInfoRepository;
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

    @Override
    public void saveMessage(MessageDto message) {
        message.setCreated_at(LocalDateTime.now());
        message.setUpdated_at(LocalDateTime.now());
        userInfoRepository.findById(message.getUser_id());
        rentalRepository.findById(message.getRental_id());
        messageRepository.save(messageMapper.toEntity(message));
    }

}
