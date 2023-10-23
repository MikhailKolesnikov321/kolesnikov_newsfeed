package com.example.kolesnikov_advancedServer.controller;
import com.example.kolesnikov_advancedServer.dto.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.dto.RegisterUserDto;
import com.example.kolesnikov_advancedServer.service.impl.UserServiceImpl;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/v1/auth/")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("register")
    public ResponseEntity registration(@Validated @RequestBody RegisterUserDto userDto){
        return new ResponseEntity(CustomSuccessResponse.ok(userServiceImpl.register(userDto)), HttpStatus.OK);
    }
}
