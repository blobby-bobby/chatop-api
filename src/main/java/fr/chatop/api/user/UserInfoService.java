package fr.chatop.api.user;

import fr.chatop.api.auth.UpdateUserRequest;
import fr.chatop.api.exceptionhandler.BadCredentialsException;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserInfoService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws EntityNotFoundException;

    void createUser(UserInfo userInfo) throws BadCredentialsException;

    UserInfo getUserByUsername(String username) throws EntityNotFoundException;

    UserInfo getUserById(Long id) throws EntityNotFoundException;

    UserInfo modifyUser(String username, UpdateUserRequest request) throws BadCredentialsException;
}
