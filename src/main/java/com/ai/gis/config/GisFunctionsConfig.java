package com.ai.gis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Configuration;
import java.util.function.Function;
import java.util.List;


@Configuration
public class GisFunctionsConfig {

    // 工具 1：查设施的返回结构
    public record PoiVo(String name, String category, double longitude, double latitude) {}

    // 工具 2：路线规划的返回结构
    public record RouteVo(String duration, double distanceInMeters) {}

    // --- Tool 1: 专门查周边的函数 ---
//    @Bean
//    @Description("查询指定经纬度周边的兴趣点（POI）设施。")
//    public Function<PoiRequest, List<PoiVo>> findNearbyPois() {
//        return request -> {
//            // 这里从数据库查出来的是 List<PoiVo> 结构
//            // 返回给 Spring AI 后，它会把这个 List 原封不动交回给内存
//            return poiService.queryFromDb(request);
//        };
//    }
//
//    // --- Tool 2: 专门查路线的函数（返回结构完全不同） ---
//    @Bean
//    @Description("计算两点之间的驾车路线规划。")
//    public Function<RouteRequest, RouteVo> getRoutePlanning() {
//        return request -> {
//            // 返回的是 RouteVo 结构
//            return routeService.calculate(request);
//        };
//    }
}
