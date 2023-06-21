package com.challenge.bci.dto.user;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.challenge.bci.dto.phone.PhoneDtoResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {
    
    private UUID id;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss a", timezone = "GMT-3")
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss a", timezone = "GMT-3")
    private Date lastLogin;

    private String name;

    private String email;

    private String password;

    private Set<PhoneDtoResponse> phones;

    private String token;

    private boolean isActive;

}
