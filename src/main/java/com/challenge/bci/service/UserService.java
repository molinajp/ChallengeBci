package com.challenge.bci.service;

import com.challenge.bci.dto.user.UserDtoRequest;
import com.challenge.bci.dto.user.UserDtoResponse;

public interface UserService {

    UserDtoResponse initAndSaveUser(UserDtoRequest user);

    UserDtoResponse loginUser(String token);


}
