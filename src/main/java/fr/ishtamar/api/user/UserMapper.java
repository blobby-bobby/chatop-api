package fr.ishtamar.api.user;

import fr.ishtamar.api.util.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserInfo> {
}
