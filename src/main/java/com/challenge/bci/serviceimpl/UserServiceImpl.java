package com.challenge.bci.serviceimpl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.challenge.bci.dto.user.UserDtoRequest;
import com.challenge.bci.dto.user.UserDtoResponse;
import com.challenge.bci.entity.UserEntity;
import com.challenge.bci.exception.TokenNotValidException;
import com.challenge.bci.exception.UserEmailNotUniqueException;
import com.challenge.bci.repository.UserRepository;
import com.challenge.bci.service.UserService;
import com.challenge.bci.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDtoResponse initAndSaveUser(UserDtoRequest userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new UserEmailNotUniqueException("Email " + userDto.getEmail() + " it's in use");
        } else {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserEntity userToSave = Utilities.mapUserDtoRequestToUserEntity(userDto);
            UserEntity userSaved = userRepository.save(userToSave);
            String token = Utilities.createJwtToken(userSaved, ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
            UserDtoResponse userToReturn = Utilities.mapUserEntityToUserDtoResponse(userSaved);
            userToReturn.setToken(token);
            return userToReturn;
        }
    }

    @Override
    public UserDtoResponse loginUser(String token) {
        String prefix = "Bearer ";
        String tokenValue = token.substring(prefix.length());
        DecodedJWT decodedJWT = Utilities.decodeToken(tokenValue);
        Optional<UserEntity> userEntityOptional = userRepository.findById(UUID.fromString(decodedJWT.getSubject()));
        if (userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setLastLogin(new Date());
            userRepository.save(userEntity);

            UserDtoResponse userDtoResponse = Utilities.mapUserEntityToUserDtoResponse(userEntity);
            userDtoResponse.setToken(Utilities.createJwtToken(userEntity, ServletUriComponentsBuilder.fromCurrentRequest().toUriString()));
            return userDtoResponse;
        } else {
            throw new TokenNotValidException("Token not valid!");
        }
    }

}