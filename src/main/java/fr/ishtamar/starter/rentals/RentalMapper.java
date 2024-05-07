package fr.ishtamar.starter.rentals;

import fr.ishtamar.starter.util.EntityMapper;
import fr.ishtamar.starter.user.UserInfoServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public abstract class RentalMapper implements EntityMapper<RentalDto, Rental> {

    @Autowired
    UserInfoServiceImpl userInfoService;

    @Mappings({
            @Mapping(target="user", expression="java(this.userInfoService.getUserById(rentalDto.getUser_id()))")
    })
    public abstract Rental toEntity(RentalDto rentalDto);

    @Mappings({
            @Mapping(source= "rental.user.id",target="user_id")
    })
    public abstract RentalDto toDto(Rental rental);
}
