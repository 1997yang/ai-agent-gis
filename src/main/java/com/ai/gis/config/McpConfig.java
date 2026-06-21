package com.ai.gis.config;

//import org.springframework.ai.mcp.client.McpClient;
//import org.springframework.ai.mcp.client.transport.ProcessClientTransport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
public class McpConfig {
//    @Bean
//    public McpClient mcpClient() {
//        // 配置要启动的外部 MCP 服务命令（例如 Node.js 编写的文件系统服务）
//        ProcessClientTransport transport = new ProcessClientTransport(
//                "node",
//                List.of("path/to/mcp-server-filesystem/index.js") // 外部服务入口
//        );
//
//        // 构建并初始化 MCP 客户端
//        McpClient client = McpClient.builder()
//                .transport(transport)
//                .build();
//
//        // 同步初始化连接、协商协议版本与能力
//        client.initialize();
//
//        return client;
//    }
}
