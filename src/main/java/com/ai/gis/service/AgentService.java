package com.ai.gis.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    @Autowired
    private final ChatClient chatClient;
    // 假设你已经配置好了 ChatMemory 的 Bean
    @Autowired
    private ChatMemory chatMemory;

//    @Autowired
//    private GisDataService gisDataService;

    public AgentService(ChatClient.Builder chatClientBuilder,
                        ChatMemory chatMemory,
                        PoiService gisFunctions) { // 直接注入你的 GisFunctions 实例

        this.chatClient = chatClientBuilder
                .defaultSystem("你是一个专业的 GIS AI 助手。你可以利用地理空间工具（如查询周边设施）来回答用户的地理空间相关问题。请用友好、简洁的语言回复。")
                .defaultAdvisors(
                        // 绑定对话记忆 Advisor
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                // 直接把加了 @Tool 注解的实例丢进去，Spring AI 底层会自动解析带有 @Tool 的方法并注册为工具
                .defaultTools(gisFunctions)
                .build();
    }

    public String chat(String conversationId, String userId, String userMessage) {
//        return chatClient.prompt()
//                .user(userMessage)
//                // 绑定当前会话的上下文 ID，保留最近 10 轮对话记忆
//                .advisors(new MessageChatMemoryAdvisor(chatMemory, conversationId, 10))
//                // 挂载提供给大模型调度的工具函数名称（对应你在 @Bean 中注册的方法名）
//                .functions("findNearbyPois", "getRoutePlanning")
//                .call()
//                .content();

        return chatClient.prompt()
                .user(userMessage)
                // 严格使用接口中定义的原生常量 ChatMemory.CONVERSATION_ID
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId).param("userId", userId))
                .call()
                .content();
    }
}