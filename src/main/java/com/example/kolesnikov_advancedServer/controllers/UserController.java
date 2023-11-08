package com.example.kolesnikov_advancedServer.controllers;

import com.example.kolesnikov_advancedServer.JwtToken.JwtUserDetails;
import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.PublicUserDto;
import com.example.kolesnikov_advancedServer.dtos.PutUserDto;
import com.example.kolesnikov_advancedServer.services.impl.UserServiceImpl;
import com.example.kolesnikov_advancedServer.validations.ValidationConstants;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public ResponseEntity getUserInfoById(@Length(min = 36, max = 36, message = ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED)
            @PathVariable String id) {
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.getUserInfo(String.valueOf(id))), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(Authentication authentication) {
        authentication.getAuthorities();
        UUID id = ((JwtUserDetails) (authentication.getPrincipal())).getId();
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.getUserInfo(String.valueOf(id))), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity putUserNewData(Authentication authentication, @Valid @RequestBody PutUserDto putUserDto){
        authentication.getAuthorities();
        UUID id = ((JwtUserDetails)(authentication.getPrincipal())).getId();
        return new ResponseEntity<>(CustomSuccessResponse.ok(userService.setUserNewData(id, putUserDto)), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(Authentication authentication){
        UUID id = ((JwtUserDetails)(authentication.getPrincipal())).getId();
        return new ResponseEntity(userService.deleteUser(id), HttpStatus.OK);
    }
}

