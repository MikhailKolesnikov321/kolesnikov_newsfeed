package com.example.kolesnikov_advancedServer.dto;

import com.example.kolesnikov_advancedServer.validations.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthUserDto {
    @NotNull(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    @Length(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID,
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$")
    private String email;

    @Length(min = 3, max = 25, message = ValidationConstants.PASSWORD_NOT_VALID)
    @NotNull(message = ValidationConstants.PASSWORD_NOT_VALID)
    private String password;
}
