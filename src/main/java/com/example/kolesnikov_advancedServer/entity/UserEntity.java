package com.example.kolesnikov_advancedServer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String avatar;
    private String email;
    private String userName;
    private String password;
    private String role = "user";
    private String token;
}
