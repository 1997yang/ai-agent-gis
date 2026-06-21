package com.ai.gis.service;

import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryService {
    private final JdbcChatMemoryRepository memoryRepository;

    public HistoryService(JdbcChatMemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    public List<?> getHistory(String conversationId) {
        // JdbcChatMemoryRepository 提供了根据 conversationId 获取原始存储数据的方法
        return memoryRepository.findByConversationId(conversationId);
    }
}
