package com.ai.gis.controller;

import com.ai.gis.common.Result;
import com.ai.gis.entity.ChatSession;
import com.ai.gis.service.AgentService;
import com.ai.gis.service.HistoryService;
import com.ai.gis.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
//@CrossOrigin(origins = "*") // 方便前端联调，生产环境按需配置
public class AgentController {

    private final AgentService agentService;
    private final HistoryService historyService;
    private final SessionService sessionService;

    public AgentController(AgentService agentService, HistoryService historyService, SessionService sessionService) {
        this.agentService = agentService;
        this.historyService = historyService;
        this.sessionService = sessionService;
    }

    public record ChatRequest(String conversationId, String userId, String message) {}

    @PostMapping("/chat")
    public Result chat(@RequestBody ChatRequest request) {
        System.out.println("-----chat");
        sessionService.saveSession(request.conversationId(), request.userId(),request.message());
        String aiAnswer = agentService.chat(request.conversationId(), request.userId(), request.message());
        return Result.ok(aiAnswer);
    }

    @GetMapping("/record")
    public Result record(@RequestParam String userId) {
        System.out.println("-----record");
        List<?> record = historyService.getHistory(userId);
        return Result.ok(record);
    }

    @GetMapping("/getSession")
    public Result getSession(@RequestParam String userId) {
        System.out.println("-----getSession");
        List<ChatSession> sessions = sessionService.getSessionByUser(userId);
        return Result.ok(sessions);
    }
}