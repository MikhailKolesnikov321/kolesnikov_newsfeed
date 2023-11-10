package com.example.kolesnikov_advancedServer.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetNewsOutDto {

    private String description;
    private Long id;
    private String image;
    private List<TagDto> tags;
    private String title;
    private UUID userId;
    private String username;
}
