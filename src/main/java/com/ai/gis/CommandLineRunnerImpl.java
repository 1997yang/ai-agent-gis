package com.ai.gis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("应用启动完成：执行CommandLineRunner方法完成资源初始化");
    }
}
