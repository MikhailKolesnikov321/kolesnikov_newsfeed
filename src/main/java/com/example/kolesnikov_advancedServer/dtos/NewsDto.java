package com.example.kolesnikov_advancedServer.dtos;

import com.example.kolesnikov_advancedServer.validations.ValidationConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewsDto {

    @NotBlank(message = ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    @Length(min = 3, max = 130, message = ValidationConstants.NEWS_IMAGE_LENGTH_NOT_VALID)
    private String image;

    @NotBlank(message = ValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT)
    @Length(min =3, max = 160, message = ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID)
    private String description;

    @NotBlank(message = ValidationConstants.NEWS_TITLE_NOT_NULL)
    @Length(min = 3, max = 160, message = ValidationConstants.NEWS_TITLE_SIZE_NOT_VALID)
    private String title;

    private List<String> tags;

}
