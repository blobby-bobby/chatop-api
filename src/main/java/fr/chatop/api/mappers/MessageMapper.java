package fr.chatop.api.mappers;

import fr.chatop.api.dto.MessageDto;
import fr.chatop.api.entities.Message;
import fr.chatop.api.services.RentalService;
import fr.chatop.api.services.impl.UserInfoServiceImpl;
import fr.chatop.api.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public abstract class MessageMapper implements EntityMapper<MessageDto, Message> {
    @Autowired
    UserInfoServiceImpl userInfoService;

    @Autowired
    RentalService rentalService;

    @Mappings({
            @Mapping(target = "user", expression = "java(this.userInfoService.getUserById(messageDto.getUser_id()))"),
            @Mapping(target = "rental", expression = "java(this.rentalService.getRentalById(messageDto.getRental_id()))")
    })

    public abstract Message toEntity(MessageDto messageDto);

    @Mappings({
            @Mapping(source = "message.user.id", target = "user_id"),
            @Mapping(source = "message.rental.id", target = "rental_id")
    })
    public abstract MessageDto toDto(Message message);
}
