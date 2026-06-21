package com.ai.gis.entity;
import lombok.Data;

@Data
public class PoiLocation {
    private Long id;
    private String name;
    private String category;
    // 简化起见，经纬度坐标作为属性返回，实际可直接返回 GeoJSON
    private Double longitude;
    private Double latitude;
    // 如果数据库直接查询出 geom 字段为 WKT 字符串（如 "POINT(116.3979 39.9087)"），
    // 可以通过自定义 setter/解析方法将其拆解为经度和纬度
    public void setGeom(String geomStr) {
        if (geomStr != null && geomStr.startsWith("POINT(") && geomStr.endsWith(")")) {
            String coords = geomStr.substring(6, geomStr.length() - 1);
            String[] parts = coords.split(" ");
            if (parts.length >= 2) {
                this.longitude = Double.parseDouble(parts[0]);
                this.latitude = Double.parseDouble(parts[1]);
            }
        }
    }
}
