package fr.ishtamar.api.messages;

import fr.ishtamar.api.rentals.RentalService;
import fr.ishtamar.api.user.UserInfoServiceImpl;
import fr.ishtamar.api.util.EntityMapper;
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
