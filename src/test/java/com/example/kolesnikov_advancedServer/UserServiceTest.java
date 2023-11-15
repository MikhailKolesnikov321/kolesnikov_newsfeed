package com.example.kolesnikov_advancedServer;

import com.example.kolesnikov_advancedServer.JwtToken.JwtTokenProvider;
import com.example.kolesnikov_advancedServer.dtos.AuthDto;
import com.example.kolesnikov_advancedServer.dtos.LoginUserDto;
import com.example.kolesnikov_advancedServer.dtos.PublicUserDto;
import com.example.kolesnikov_advancedServer.dtos.PutUserDto;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.exceptions.CustomException;
import com.example.kolesnikov_advancedServer.mappers.AuthMappers;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthMappers userMapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl( userMapper,userRepo, passwordEncoder, jwtTokenProvider);
    }

    @Test
    void testRegisterUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("test@example.com");
        registerUserDto.setPassword("password");

        LoginUserDto loginUserDto = mock(LoginUserDto.class);

        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setEmail("test@example.com");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(existingUserEntity));

        assertThrows(CustomException.class, () -> userService.register(registerUserDto));
        verify(userRepo, times(1)).findByEmail("test@example.com");
        verify(userRepo, never()).save(any());

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());

        UserEntity registeredUserEntity = new UserEntity();
        registeredUserEntity.setId(UUID.randomUUID());
        registeredUserEntity.setEmail("test@example.com");

        when(userMapper.RegisterUserDtoToUserEntity(registerUserDto)).thenReturn(registeredUserEntity);
        when(userMapper.UserEntityToLoginUserDto(registeredUserEntity)).thenReturn(loginUserDto);
        when(jwtTokenProvider.generateToken(registeredUserEntity.getId().toString())).thenReturn("token");

        LoginUserDto result = userService.register(registerUserDto);

        verify(userRepo, times(1)).save(registeredUserEntity);
        verify(userMapper, times(1)).UserEntityToLoginUserDto(registeredUserEntity);
        verify(jwtTokenProvider, times(1)).generateToken(registeredUserEntity.getId().toString());

        assertEquals(loginUserDto, result);
    }

    @Test
    void testLogin_Success() {
        AuthDto authUserDto = new AuthDto();
        authUserDto.setEmail("test@example.com");
        authUserDto.setPassword("correctPassword");

        UserEntity userEntity = new UserEntity();
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);
        userEntity.setEmail("test@example.com");
        userEntity.setAvatar("avatar");
        userEntity.setName("username");
        userEntity.setPassword(passwordEncoder.encode("correctPassword"));

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("correctPassword", userEntity.getPassword())).thenReturn(true);
        when(userMapper.UserEntityToLoginUserDto(userEntity)).thenReturn(new LoginUserDto());
        when(jwtTokenProvider.generateToken(any())).thenReturn("mockedToken");

        LoginUserDto result = userService.login(authUserDto);

        assertNotNull(result);
        assertEquals("mockedToken", result.getToken());
        verify(userRepo, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("correctPassword", userEntity.getPassword());
        verify(jwtTokenProvider, times(1)).generateToken(userId.toString());
    }

    @Test
    void testLogin_InvalidPassword() {
        AuthDto authUserDto = new AuthDto();
        authUserDto.setEmail("test@example.com");
        authUserDto.setPassword("incorrectPassword");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword(passwordEncoder.encode("correctPassword"));

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("incorrectPassword", userEntity.getPassword())).thenReturn(false);

        assertThrows(CustomException.class, () -> userService.login(authUserDto));
    }

    @Test
    void testLogin_UserNotFound() {
        AuthDto authUserDto = new AuthDto();
        authUserDto.setEmail("nonexistent@example.com");
        authUserDto.setPassword("password");

        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> userService.login(authUserDto));
    }

    @Test
    void testGetAllUsers() {
        UserEntity user1 = new UserEntity();
        user1.setId(UUID.randomUUID());
        user1.setEmail("user1@example.com");

        UserEntity user2 = new UserEntity();
        user2.setId(UUID.randomUUID());
        user2.setEmail("user2@example.com");

        PublicUserDto publicUserDto1 = new PublicUserDto();
        publicUserDto1.setId(user1.getId());
        publicUserDto1.setEmail(user1.getEmail());

        PublicUserDto publicUserDto2 = new PublicUserDto();
        publicUserDto2.setId(user2.getId());
        publicUserDto2.setEmail(user2.getEmail());

        when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.UserEntityToPublicUserDto(user1)).thenReturn(publicUserDto1);
        when(userMapper.UserEntityToPublicUserDto(user2)).thenReturn(publicUserDto2);

        List<PublicUserDto> result = userService.getAllUsers();
        assertEquals(2, result.size());
        assertEquals(publicUserDto1, result.get(0));
        assertEquals(publicUserDto2, result.get(1));
    }

    @Test
    void testSetUserNewData() {
        UUID userId = UUID.randomUUID();
        PutUserDto putUserDto = new PutUserDto();
        putUserDto.setEmail("newemail@example.com");
        putUserDto.setName("New Name");
        putUserDto.setAvatar("newAvatar");
        putUserDto.setRole("user");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setEmail("oldemail@example.com");
        userEntity.setName("Old Name");
        userEntity.setAvatar("oldAvatar");

        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepo.findByEmail(putUserDto.getEmail())).thenReturn(Optional.empty());
        when(userMapper.UserEntityToPublicUserDto(userEntity)).thenReturn(new PublicUserDto(
                putUserDto.getAvatar(), putUserDto.getEmail(), userId, putUserDto.getName(), putUserDto.getRole()));

        PublicUserDto result = userService.setUserNewData(userId, putUserDto);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(putUserDto.getEmail(), result.getEmail());
        assertEquals(putUserDto.getName(), result.getName());
        assertEquals(putUserDto.getAvatar(), result.getAvatar());

        verify(userRepo, times(1)).findById(userId);
        verify(userRepo, times(1)).findByEmail(putUserDto.getEmail());
        verify(userRepo, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testDeleteUser() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setEmail("test@example.com");

        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.deleteUser(userId);

        verify(userRepo, times(1)).delete(userEntity);
    }
}
