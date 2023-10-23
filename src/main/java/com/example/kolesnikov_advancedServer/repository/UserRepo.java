package com.example.kolesnikov_advancedServer.repository;
import com.example.kolesnikov_advancedServer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

}
