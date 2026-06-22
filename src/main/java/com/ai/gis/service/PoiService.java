package com.ai.gis.service;

import com.ai.gis.entity.PoiLocation;
import com.ai.gis.mapper.PoiMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiService {

    private final PoiMapper poiMapper;

    public PoiService(PoiMapper poiMapper) {
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
}