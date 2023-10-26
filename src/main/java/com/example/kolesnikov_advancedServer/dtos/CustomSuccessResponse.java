package com.example.kolesnikov_advancedServer.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomSuccessResponse<T> {
    private T data;
    private Integer statusCode;
    private Boolean success;
    private Integer[] errors;

    public static <T> CustomSuccessResponse ok(T data) {
        return new CustomSuccessResponse()
                .setData(data)
                .setStatusCode(0)
                .setSuccess(true);
    }

    public static <T> CustomSuccessResponse badRequest(Integer[] errors) {
        return new CustomSuccessResponse()
                .setErrors(errors)
                .setStatusCode(1)
                .setSuccess(false);
    }
}
