package com.example.kolesnikov_advancedServer.dto;

import com.example.kolesnikov_advancedServer.validations.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
public class RegisterUserDto {
    @NotNull(message = ValidationConstants.USER_AVATAR_NOT_NULL)
    @Length(min = 3, max = 130, message = ValidationConstants.USER_AVATAR_NOT_VALID)
    private String avatar;

    @NotNull(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    @Length(min = 3, max = 100, message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID, regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Length(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    @NotNull(message = ValidationConstants.USER_NAME_HAS_TO_BE_PRESENT)
    private String userName;

    @Length(min = 6, message = ValidationConstants.USER_PASSWORD_NOT_VALID)
    private String password;

    private String role = "user";
}
