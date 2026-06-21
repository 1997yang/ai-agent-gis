package com.ai.gis.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatSession {
    private Long id;
    private String sessionId;
    private String userId;
    private String title;
    private LocalDateTime createdTime;
}