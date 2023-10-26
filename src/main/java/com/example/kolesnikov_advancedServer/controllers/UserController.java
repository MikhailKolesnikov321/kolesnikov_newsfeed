package com.example.kolesnikov_advancedServer.controllers;

import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import com.example.kolesnikov_advancedServer.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/auth/")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("register")
    public ResponseEntity register(@Validated @RequestBody RegisterUserDto userDto) {
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.register(userDto)), HttpStatus.OK);
    }


}
