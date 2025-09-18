package com.example.auth_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "processing_log")
@Data
public class ProcessingLog {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "input_text", nullable = false, length = 2000)
    private String inputText;

    @Column(name = "output_text", nullable = false, length = 2000)
    private String outputText;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

}


