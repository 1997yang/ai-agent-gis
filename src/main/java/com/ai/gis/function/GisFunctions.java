package com.ai.gis.function;

import com.ai.gis.entity.PoiLocation;
import com.ai.gis.mapper.PoiMapper;
import org.springframework.ai.tool.annotation.Tool; // 引入 Spring AI 1.1.x 的 Tool 注解
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GisFunctions {

    private final PoiMapper poiMapper;

    public GisFunctions(PoiMapper poiMapper) {
        this.poiMapper = poiMapper;
    }

    public record Request(double longitude, double latitude, double radiusInMeters) {}

    // 使用 @Tool 注解替代原先的 Function Bean，并提供清晰的描述
    @Tool(
            name = "findNearbyPois",
            description = "查询指定经纬度周边一定米数范围内的地理实体（POI），如加油站、医院等。"
    )
    public List<PoiLocation> findNearbyPois(Request request) {
        return poiMapper.findNearbyPois(
                request.longitude(),
                request.latitude(),
                request.radiusInMeters()
        );
    }

    @Tool(
            name = "getRoutePlanning",
            description = "查两点之间的路线规划，如从天安门到王府井怎么走？"
    )
    public List<PoiLocation> getRoutePlanning(Request request) {
        return poiMapper.findNearbyPois(
                request.longitude(),
                request.latitude(),
                request.radiusInMeters()
        );
    }
    //getRoutePlanning（）
}