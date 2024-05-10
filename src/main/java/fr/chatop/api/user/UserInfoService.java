package fr.chatop.api.user;

import fr.chatop.api.auth.UpdateUserRequest;
import fr.chatop.api.exceptionhandler.BadCredentialsException;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserInfoService extends UserDetailsService {
    @Override
    /**
     * Retrieves user by the unique username
     * @param username
     * @return User corresponding to username
     * @throws EntityNotFoundException Username not found
     */
    UserDetails loadUserByUsername(String username) throws EntityNotFoundException;

    /**
     * register a new user in the database
     * @param userInfo the user data
     * @throws BadCredentialsException email is already used, password is not conform
     */
    void createUser(UserInfo userInfo) throws BadCredentialsException;

    /**
     * Retrieve the user by the username
     * @param username
     * @return UserInfo the user corresponding data
     * @throws EntityNotFoundException username not found in the database
     */
    UserInfo getUserByUsername(String username) throws EntityNotFoundException;

    /**
     * Retrieve the user by the id
     * @param id
     * @return UserInfo the user corresponding data
     * @throws EntityNotFoundException id not found in the database
     */
    UserInfo getUserById(Long id) throws EntityNotFoundException;

    /**
     * update the user in the database
     * @param username
     * @param request the object to update the user
     * @return the update user data
     * @throws BadCredentialsException username not found
     */
    UserInfo modifyUser(String username, UpdateUserRequest request) throws BadCredentialsException;
}
