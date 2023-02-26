package com.getmoex.moexservice.exception;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ErrorDto {
    private String error;
}
