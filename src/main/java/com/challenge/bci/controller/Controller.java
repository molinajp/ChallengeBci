package com.challenge.bci.controller;

import com.challenge.bci.dto.user.UserDtoRequest;
import com.challenge.bci.dto.user.UserDtoResponse;
import com.challenge.bci.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDtoResponse> signUpUser(@RequestBody @Valid UserDtoRequest user) {
        UserDtoResponse userToReturn = userService.initAndSaveUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(uri).body(userToReturn);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(userService.loginUser(token));
    }

}
