package com.example.kolesnikov_advancedServer.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {
    private T data;
    private int numberOfElements;

    public static <T> PageableResponse ok(T data, int count) {
        return new PageableResponse()
                .setData(data)
                .setNumberOfElements(count);
    }
}
