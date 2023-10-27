package com.example.kolesnikov_advancedServer.exceptions;

import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException {

    private final ErrorCodes errorCodes;

    public CustomException(ErrorCodes errorCodes) {
        super(errorCodes.getMessage());
        this.errorCodes = errorCodes;
    }
}
