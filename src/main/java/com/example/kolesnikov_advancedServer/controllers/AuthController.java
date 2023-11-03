package com.example.kolesnikov_advancedServer.controllers;

import com.example.kolesnikov_advancedServer.dtos.AuthDto;
import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import com.example.kolesnikov_advancedServer.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

    private final UserServiceImpl userService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterUserDto userDto) {
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.register(userDto)), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody AuthDto authUserDto) {
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.login(authUserDto)), HttpStatus.OK);
    }
}
