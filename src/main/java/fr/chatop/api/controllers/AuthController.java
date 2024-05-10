package fr.chatop.api.controllers;

import fr.chatop.api.auth.AuthRequest;
import fr.chatop.api.auth.CreateUserRequest;
import fr.chatop.api.auth.UpdateUserRequest;
import fr.chatop.api.exceptionhandler.BadCredentialsException;
import fr.chatop.api.user.UserDto;
import fr.chatop.api.user.UserInfo;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import fr.chatop.api.user.UserMapper;
import fr.chatop.api.security.JwtService;
import fr.chatop.api.user.UserInfoService;
import fr.chatop.api.user.UserInfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    static final String TOKEN="token";

    public AuthController(UserInfoServiceImpl service, JwtService jwtService, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.service=service;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        this.userMapper=userMapper;
    }

    @Operation(hidden=true)
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @Operation(summary = "register new user",responses={
            @ApiResponse(responseCode="200", description = "User successfully created"),
            @ApiResponse(responseCode="400", description = "User already exists")
    })
    @PostMapping("/register")
    public Map<String,String> addNewUser(@Valid @RequestBody @Parameter(description = "Request object for registering a new user") CreateUserRequest request) throws ConstraintViolationException {
        UserInfo userInfo=UserInfo.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles("ROLE_USER")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        service.createUser(userInfo);
        Map<String,String>map=new HashMap<>();
        map.put(TOKEN,jwtService.generateToken(userInfo.getEmail()));
        return map;
    }

    @Operation(summary = "logins user and returns JWT",responses={
            @ApiResponse(responseCode="200", description = "Token successfully created"),
            @ApiResponse(responseCode="404", description = "Username not found")
    })
    @PostMapping("/login")
    public Map<String,String> authenticateAndGetToken(@RequestBody @Parameter(description = "Request object for login") AuthRequest authRequest) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            Map<String,String>map=new HashMap<>();
            map.put(TOKEN,jwtService.generateToken(authRequest.getEmail()));
            return map;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @Operation(summary = "gets personal data from logged in user",responses={
            @ApiResponse(responseCode="200", description = "Personal data is displayed"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/me")
    @Secured("ROLE_USER")
    public UserDto userProfile(@RequestHeader(value="Authorization",required=false) @Parameter(description = "JSON web token to ensure authorization") String jwt) {
        return userMapper.toDto(service.getUserByUsername(jwtService.extractUsername(jwt.substring(7))));
    }

    @Operation(summary = "changes personal data from logged in user",responses={
            @ApiResponse(responseCode="200", description = "Personal data is changed, new JWT is displayed"),
            @ApiResponse(responseCode="400", description = "Email is already used or password is not valid"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @PutMapping("/me")
    @Secured("ROLE_USER")
    public Map<String,String> userModifyProfile(
            @RequestHeader(value="Authorization",required=false) @Parameter(description = "JSON web token to ensure authorized request") String jwt,
            @Valid @RequestBody @Parameter(description = "Request object for updating the user's profile") UpdateUserRequest request
    ) throws EntityNotFoundException, BadCredentialsException, ConstraintViolationException {
        UserInfo candidate=service.modifyUser(jwtService.extractUsername(jwt.substring(7)),request);

        //prepare a new JWT to show (not executed if there's an error before)
        Map<String,String>map=new HashMap<>();
        map.put(TOKEN,jwtService.generateToken(candidate.getEmail()));

        return map;
    }
}
