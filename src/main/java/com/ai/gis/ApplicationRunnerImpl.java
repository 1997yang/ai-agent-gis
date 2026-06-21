package com.ai.gis;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.FileReader;
import java.io.IOException;
@Order(2)
@Component
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("应用启动完成：执行ApplicationRunner方法完成资源初始化");

//        try (FileReader reader = new FileReader("config.json")) {
//            // 读取文件内容
//            StringBuilder stringBuilder = new StringBuilder();
//            int character;
//            while ((character = reader.read()) != -1) {
//                stringBuilder.append((char) character);
//            }
//            // 使用 Gson 解析 JSON
//            JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
//            String name = jsonObject.getString("name");
//            int age = jsonObject.getIntValue("age");
//            System.out.println("Config value: " + name);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
