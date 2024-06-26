package fr.chatop.api.services.impl;

import fr.chatop.api.exceptionhandler.BadCredentialsException;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import fr.chatop.api.requests.auth.UpdateUserRequest;
import fr.chatop.api.security.UserInfoDetails;
import fr.chatop.api.services.UserInfoService;
import fr.chatop.api.entities.UserInfo;
import fr.chatop.api.repositories.UserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static fr.chatop.api.security.SecurityConfig.passwordEncoder;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository repository;

    public UserInfoServiceImpl(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new EntityNotFoundException(UserDetails.class,"username",username));
    }

    @Override
    public void createUser(UserInfo userInfo) throws BadCredentialsException {
        Optional<UserInfo> userDetail = repository.findByEmail(userInfo.getEmail());
        if (userDetail.isPresent()){
            throw new BadCredentialsException();
        } else {
            userInfo.setPassword(passwordEncoder().encode(userInfo.getPassword()));
            repository.save(userInfo);
        }
    }

    @Override
    public UserInfo getUserByUsername(String username) throws EntityNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(UserInfo.class,"email",username));
    }

    @Override
    public UserInfo getUserById(Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserDetails.class,"id",id.toString()));
    }

    @Override
    public UserInfo modifyUser(String username, UpdateUserRequest request) throws BadCredentialsException, EntityNotFoundException {
        UserInfo userInfo=this.getUserByUsername(username);
        userInfo.setUpdated_at(LocalDateTime.now());

        if(passwordEncoder().matches(request.getOldPassword(),userInfo.getPassword())) {
            if (request.getName() != null && !request.getName().isEmpty()) userInfo.setName(request.getName());
            if (request.getEmail() != null && !request.getEmail().isEmpty()) userInfo.setEmail(request.getEmail());
            if (request.getPassword() != null && !request.getPassword().isEmpty())
                userInfo.setPassword(passwordEncoder().encode(request.getPassword()));

            try {
                return repository.save(userInfo);
            } catch (Exception e) {
                throw new BadCredentialsException();
            }
        } else {
            throw new BadCredentialsException();
        }
    }
}
