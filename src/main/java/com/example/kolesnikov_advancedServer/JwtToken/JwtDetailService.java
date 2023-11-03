package com.example.kolesnikov_advancedServer.JwtToken;

import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.exceptions.CustomException;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class JwtDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UUID id = UUID.fromString(name);
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.UNAUTHORISED));
        return JwtUserFactory.create(userEntity);
    }
}
