package fr.chatop.api.mappers;

import fr.chatop.api.dto.UserDto;
import fr.chatop.api.entities.UserInfo;
import fr.chatop.api.util.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserInfo> {
}
