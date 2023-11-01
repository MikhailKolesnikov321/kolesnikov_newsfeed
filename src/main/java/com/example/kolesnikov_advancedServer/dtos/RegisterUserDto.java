package com.example.kolesnikov_advancedServer.dtos;

import com.example.kolesnikov_advancedServer.validations.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterUserDto {

    @Length(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID,
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$")
    private String email;

    @Length(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USERNAME_HAS_TO_BE_PRESENT)
    private String name;

    @Length(min = 3, max = 25, message = ValidationConstants.PASSWORD_NOT_VALID)
    @NotBlank(message = ValidationConstants.PASSWORD_NOT_VALID)
    private String password;

    @Length(min = 3, max = 25, message = ValidationConstants.USER_AVATAR_NOT_NULL)
    @NotBlank(message = ValidationConstants.USER_AVATAR_NOT_NULL)
    private String avatar;

    @NotBlank(message = ValidationConstants.USER_ROLE_NOT_NULL)
    @Length(min = 4, max = 4, message = ValidationConstants.UNKNOWN)
    private String role;
}
