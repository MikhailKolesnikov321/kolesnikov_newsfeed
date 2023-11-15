package com.example.kolesnikov_advancedServer.entities;

import lombok.Data;
import org.hibernate.type.StringNVarcharType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Table(name = "logs")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;
    private String methodName;
    private String url;
    private String ip;
    private int responseStatus;
    private String hostName;
    private String queryParameters;
    private long executionTime;
    private LocalDateTime timestamp;
}
