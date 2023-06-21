package com.challenge.bci.dto.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDto {

    private List<ExceptionDto> errors;

    public void addException(final ExceptionDto exception) {
        errors.add(exception);
    }

}
