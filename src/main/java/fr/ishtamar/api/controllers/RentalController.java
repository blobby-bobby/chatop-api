package fr.ishtamar.api.controllers;

import fr.ishtamar.api.exceptionhandler.GenericException;
import fr.ishtamar.api.user.UserInfo;
import fr.ishtamar.api.user.UserInfoService;
import fr.ishtamar.api.user.UserInfoServiceImpl;
import fr.ishtamar.api.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.api.filetransfer.FileUploadUtil;
import fr.ishtamar.api.rentals.*;
import fr.ishtamar.api.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "gets all rentals from database",responses={
            @ApiResponse(responseCode="200", description = "All rentals are displayed"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping(value="")
    public Map<String, List<RentalDto>> getAllRentals() {
        Map<String, List<RentalDto>> map = new HashMap<>();
        map.put("rentals", rentalMapper.toDto(rentalService.getAllRentals()));
        return map;
    }

    @Operation(summary = "retrieve a rental with its id",responses={
            @ApiResponse(responseCode="200", description = "The selected rental is displayed"),
            @ApiResponse(responseCode="404", description = "Access unauthorized")
    })
    @GetMapping(value = "/{id}")
    public RentalDto getRental(@PathVariable("id") @Parameter(description = "the id of the rental") final Long id) {
        return rentalMapper.toDto(rentalService.getRentalById(id));
    }

    @Operation(summary = "save a new rental to the database",responses={
            @ApiResponse(responseCode="200", description = "New rental successfully created"),
            @ApiResponse(responseCode="404", description = "Access unauthorized")
    })
    @PostMapping(value="",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public Map<String, String> createRental(
            @Parameter(description = "Request object for creating a rental") CreateRentalRequest createRentalRequest,
            @RequestHeader(value="Authorization") @Parameter(description = "JSON web token to ensure authorized request") String jwt
    ) throws IOException {

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

    @Operation(summary = "retrieve a rental with its id and update it",responses={
            @ApiResponse(responseCode="200", description = "Rental successfully updated"),
            @ApiResponse(responseCode="404", description = "Access unauthorized")
    })
    @PutMapping(value="/{id}")
    @Secured("ROLE_USER")
    public RentalDto updateRental(
            @Parameter(description = "the id of the rental") @PathVariable Long id,
            @Parameter(description = "JSON web token to ensure authorized request") @RequestHeader(value = "Authorization") String jwt,
            @Parameter(description = "Request object for updating a rental") @Valid UpdateRentalRequest updateRentalRequest
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
