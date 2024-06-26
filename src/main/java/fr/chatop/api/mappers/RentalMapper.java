package fr.chatop.api.mappers;

import fr.chatop.api.dto.RentalDto;
import fr.chatop.api.entities.Rental;
import fr.chatop.api.services.impl.UserInfoServiceImpl;
import fr.chatop.api.util.EntityMapper;
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
            @Mapping(target="owner", expression="java(this.userInfoService.getUserById(rentalDto.getOwner_id()))")
    })
    public abstract Rental toEntity(RentalDto rentalDto);

    @Mappings({
            @Mapping(source= "rental.owner.id",target="owner_id")
    })
    public abstract RentalDto toDto(Rental rental);
}
