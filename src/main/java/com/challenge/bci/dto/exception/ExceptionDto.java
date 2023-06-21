package com.challenge.bci.dto.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private Date timestamp;

    private int code;

    private String details;

}
