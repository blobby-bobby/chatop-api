package fr.ishtamar.starter.controllers;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.filetransfer.FileUploadUtil;
import fr.ishtamar.starter.rentals.*;
import fr.ishtamar.starter.security.JwtService;
import fr.ishtamar.starter.user.UserInfo;
import fr.ishtamar.starter.user.UserInfoService;
import fr.ishtamar.starter.user.UserInfoServiceImpl;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final JwtService jwtService;
    private final UserInfoService userInfoService;
    private final RentalService rentalService;
    private final RentalMapper rentalMapper;
    private final FileUploadUtil fileUploadUtil;

    public RentalController(
            JwtService jwtService,
            UserInfoServiceImpl userInfoService,
            RentalServiceImpl rentalService,
            RentalMapper rentalMapper,
            FileUploadUtil fileUploadUtil
    ) {
        this.jwtService = jwtService;
        this.userInfoService = userInfoService;
        this.rentalService = rentalService;
        this.rentalMapper = rentalMapper;
        this.fileUploadUtil = fileUploadUtil;
    }

    // get all rentals
    @GetMapping(value="")
    public Map<String, List<RentalDto>> getAllRentals() {
        Map<String, List<RentalDto>> map = new HashMap<>();
        map.put("rentals", rentalMapper.toDto(rentalService.getAllRentals()));
        return map;
    }

    // get one rental by id
    @GetMapping(value = "/{id}")
    public RentalDto getRental(@PathVariable("id") final Long id) {
        return rentalMapper.toDto(rentalService.getRentalById(id));
    }

    // create a rental
    @PostMapping(value="",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public Map<String, String> createRental(CreateRentalRequest createRentalRequest, @RequestHeader(value="Authorization") String jwt) throws IOException {

        String username = jwtService.extractUsername(jwt.substring(7));
        UserInfo owner = userInfoService.getUserByUsername(username);

        Rental rental = Rental.builder()
                .name(createRentalRequest.getName())
                .price(createRentalRequest.getPrice())
                .surface(createRentalRequest.getSurface())
                .description(createRentalRequest.getDescription())
                .picture(fileUploadUtil.saveFile(createRentalRequest.getPicture()))
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .owner(owner)
                .build();

        rentalService.createRental(rental);

        Map<String,String>map=new HashMap<>();
        map.put("message","Rental successfully created");
        return map;
    }

    // update a rental
    @PutMapping(value="/{id}")
    @Secured("ROLE_USER")
    public RentalDto updateRental(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String jwt,
            @Valid UpdateRentalRequest updateRentalRequest
    ) throws GenericException, EntityNotFoundException {
        String username = jwtService.extractUsername(jwt.substring(7));
        UserInfo sender = userInfoService.getUserByUsername(username);

        // retrieve the rental in the database with its id
        Rental rentalToUpdate = rentalService.getRentalById(id);

        // checks expected owner_id
        if (!Objects.equals(sender.getId(), rentalToUpdate.getOwner().getId())) {
            throw new GenericException("Owner id does not match");
        } else {
            return rentalMapper.toDto(rentalService.updateRental(rentalToUpdate, updateRentalRequest));
        }

    }
}
