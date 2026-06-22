package com.ai.gis.service;

import com.ai.gis.entity.ChatSession;
import com.ai.gis.mapper.ChatSessionMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    private final ChatSessionMapper chatSessionMapper;

    // 构造函数注入 Mapper
    public SessionService(ChatSessionMapper chatSessionMapper) {
        this.chatSessionMapper = chatSessionMapper;
    }

    public Integer saveSession(String conversationId, String userId, String title){
        ChatSession session = new ChatSession();
        session.setSessionId(conversationId);
        session.setUserId(userId);
        session.setTitle(title);
        session.setCreatedTime(LocalDateTime.now());
        return chatSessionMapper.insertChatSession(session);
    }

    public List<ChatSession> getSessionByUser(String userId){
        return chatSessionMapper.getSessionsByUserId(userId);
    }
}