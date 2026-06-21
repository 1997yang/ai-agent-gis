package com.ai.gis.mapper;
import com.ai.gis.entity.PoiLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PoiMapper {
    // 对应 PostGIS 空间查询：查找距离指定经纬度一定范围内的 POI
    List<PoiLocation> findNearbyPois(
            @Param("longitude") double longitude,
            @Param("latitude") double latitude,
            @Param("radiusInMeters") double radiusInMeters
    );
}