package com.ai.gis.service;

import com.ai.gis.common.Result;
import com.ai.gis.entity.PoiLocation;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class AmapService {
    @Value("${amap.key}")
    private String apiKey;

    @Value("${amap.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public record AmapRouteResponse(
            String status,
            String info,
            RouteDetail route
    ) {
        public record RouteDetail(
                // 高德返回的路线方案列表
                java.util.List<Path> paths
        ) {}

        public record Path(
                String distance,
                String duration
        ) {}
    }

    public record RouteParam(
            String originLngLat,
            String destLngLat
    ) {}

    public record RouteResult(
            String distanceMeters,
            String durationMinutes
    ) {}

    public record GeoParam(
            String address,
            String city
    ) {}

    public record GeoResult(
            String distanceMeters,
            String durationMinutes
    ) {}

    /**
     * 挂载 @Tool 注解供大模型调度
     */
    @Tool(description = "调用高德地图API，查询两点（经纬度坐标，如 116.481028,39.989643）之间的步行或驾车路线规划，返回距离和预计耗时。")
    public RouteResult getRoutePlanning(RouteParam param) {
        // 组装高德步行路径规划 URL (示例使用步行：/direction/walking)
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/direction/walking")
                .queryParam("key", apiKey)
                .queryParam("origin", param.originLngLat()) // 起点经纬度
                .queryParam("destination", param.destLngLat()) // 终点经纬度
                .toUriString();

        // 发起网络请求调用高德接口
        AmapRouteResponse response = restTemplate.getForObject(url, AmapRouteResponse.class);

        // 解析高德返回的数据并包装
        if (response != null && "1".equals(response.status()) && !response.route().paths().isEmpty()) {
            var path = response.route().paths().get(0);
            // 高德距离单位为米，时间单位为秒，可按需转换
            int durationSec = Integer.parseInt(path.duration());
            return new RouteResult(path.distance(), (durationSec / 60) + " 分钟");
        }

        throw new RuntimeException("高德路线规划调用失败或未找到路线");
    }

    public Result<Map<String, Object>> geo(GeoParam param){
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/direction/walking")
                .queryParam("key", apiKey)
                .queryParam("address", param.address()) // 起点经纬度
                .queryParam("city", param.city()) // 终点经纬度
                .toUriString();
        AmapRouteResponse response = restTemplate.getForObject(url, AmapRouteResponse.class);
        return Result.ok(response);
    }
}