package com.ai.gis.mapper;

import com.ai.gis.entity.ChatSession;

import java.util.List;

public interface ChatSessionMapper {
    /**
     * 插入会话（防重）
     * @param session 会话实体
     * @return 受影响行数
     */
    int insertChatSession(ChatSession session);

    List<ChatSession> getSessionsByUserId(String userId);
}