package com.example.kolesnikov_advancedServer.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseSuccessResponse {
    private Integer statusCode;
    private Boolean success;
    private Integer[] codes;

    public static BaseSuccessResponse ok() {
        return new BaseSuccessResponse()
                .setStatusCode(0)
                .setSuccess(true);
    }

    public static BaseSuccessResponse badRequest(Integer[] errors) {
        return new BaseSuccessResponse()
                .setCodes(errors)
                .setStatusCode(errors[0])
                .setSuccess(true);
    }
}
