package com.example.kolesnikov_advancedServer.JwtToken;

import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
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
        Optional<UserEntity> userOptional = userRepo.findById(id);
        UserEntity userEntity = userOptional.get();
        JwtUserDetails jwtUserDetails = JwtUserFactory.create(userEntity);
        return jwtUserDetails;
    }
}
