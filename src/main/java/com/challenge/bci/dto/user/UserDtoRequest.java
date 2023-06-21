package com.challenge.bci.dto.user;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.challenge.bci.dto.phone.PhoneDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRequest {

    private static final int MIN_SIZE_FOR_PASSWORD = 8;

    private static final int MAX_SIZE_FOR_PASSWORD = 12;

    private String name;

    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @NotNull
    private String email;

    @Size(min = MIN_SIZE_FOR_PASSWORD, max = MAX_SIZE_FOR_PASSWORD, message = "Password must have a length between 8 and 12 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Only letters and digits are allowed")
    @Pattern(regexp = "^(\\D*\\d\\D*){2}$", message = "The value should have 2 numbers")
    @Pattern(regexp = "^(?=.*[^A-Z])[^A-Z]*[A-Z][^A-Z]*$", message = "The value entered should have 1 uppercase letter")
    @NotNull
    private String password;

    private Set<PhoneDtoRequest> phones;
}
