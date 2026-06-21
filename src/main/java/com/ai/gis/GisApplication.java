/**
 * Spring Boot应用程序的入口类
 */
package com.ai.gis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 引导类，springBoot项目入口
 */
//开启Servlet组件支持
@ServletComponentScan
@SpringBootApplication
//@MapperScan("com.ai.gis.mapper")  //MapperScan批量扫描所有的Mapper接口;
//@ConfigurationPropertiesScan("com.ai.gis.config") //自动扫描包以查找带有@ConfigurationProperties注解的类，并将它们注册为Spring Bean
@EnableScheduling                   //以启用定时任务的执行;
public class GisApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GisApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GisApplication.class);
    }
}