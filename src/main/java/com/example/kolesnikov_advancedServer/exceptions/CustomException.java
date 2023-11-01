package com.example.kolesnikov_advancedServer.exceptions;

import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    public CustomException(ErrorCodes errorCodes) {
        super(errorCodes.getMessage());
    }
}
