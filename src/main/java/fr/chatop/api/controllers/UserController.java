package fr.chatop.api.controllers;

import fr.chatop.api.dto.UserDto;
import fr.chatop.api.services.UserInfoService;
import fr.chatop.api.services.impl.UserInfoServiceImpl;
import fr.chatop.api.mappers.UserMapper;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserInfoService service;
    private final UserMapper userMapper;

    public UserController(UserInfoServiceImpl service, UserMapper userMapper) {
        this.service = service;
        this.userMapper = userMapper;
    }

    @Operation(summary = "gets personal data from user by id",responses={
            @ApiResponse(responseCode="200", description = "Personal data is displayed"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public UserDto getUserById(@PathVariable("id") @Parameter(description = "the id of the user") final long id) throws EntityNotFoundException {
        return userMapper.toDto(service.getUserById(id));
    }
}
