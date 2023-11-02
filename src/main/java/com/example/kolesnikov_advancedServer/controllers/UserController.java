package com.example.kolesnikov_advancedServer.controllers;

import com.example.kolesnikov_advancedServer.JwtToken.JwtUserDetails;
import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.PublicUserDto;
import com.example.kolesnikov_advancedServer.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<PublicUserDto>>> getAllUsers() {
        List<PublicUserDto> publicUserDtos = userService.getAllUsers();
        return new ResponseEntity<>(CustomSuccessResponse.ok(publicUserDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserInfoById(@PathVariable UUID id) {
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.getUserInfoById(id)), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(Authentication authentication) {
        UUID id = ((JwtUserDetails) (authentication.getPrincipal())).getId();
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.getUserInfo(id)), HttpStatus.OK);
    }
}

