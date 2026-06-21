package com.ai.gis.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatMemoryConfig {

//    @Bean
//    public JdbcChatMemoryRepository customJdbcChatMemoryRepository(JdbcTemplate jdbcTemplate) {
//        // 👈 核心：在这里传入您自定义的表名（例如改为：my_chat_memory）
//        return new JdbcChatMemoryRepository(jdbcTemplate, "my_chat_memory");
//    }

    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(20) // 设置滑动窗口保留的历史消息条数
                .build();
    }
}
