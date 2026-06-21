package com.ai.gis.util;

import org.locationtech.jts.geom.*;

import java.util.Random;

public class StringUtil {
    /**
     * 构建 Coordinate 的坐标字符串
     * @param coordinates
     * @return
     */
    public static String buildCoordinatesString(Coordinate[] coordinates) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < coordinates.length; i++) {
            sb.append(String.format("[%f, %f]", coordinates[i].x, coordinates[i].y));
            if (i < coordinates.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    /**
     * 构建 Polygon 的坐标字符串（镂空）
     * @param polygon 多边形：由一个外部环（也称为外环）和零个或多个内部环（也称为洞）组成的
     * @return
     */
    public static String buildPolygonCoordinatesString(Polygon polygon) {
        StringBuilder sb = new StringBuilder("[");
        //多边形外部环
        sb.append(buildCoordinatesString(polygon.getExteriorRing().getCoordinates()));
        //多边形内部环
        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            sb.append(", ");
            sb.append(buildCoordinatesString(polygon.getInteriorRingN(i).getCoordinates()));
        }
        sb.append("]");
        return sb.toString();
    }
    /**
     * 构建 MultiPolygon 的坐标字符串（镂空）
     * @param multiPolygon 多边形：由一个外部环（也称为外环）和零个或多个内部环（也称为洞）组成的
     * @return
     */
    public static String buildMultiPolygonCoordinatesString(MultiPolygon multiPolygon) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
            sb.append(buildPolygonCoordinatesString(polygon));
            if (i < multiPolygon.getNumGeometries() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    /**
     * 构建 MultiLine 的坐标字符串
     * @param multiLine
     * @return
     */
    public static String buildMultiLineCoordinatesString(Geometry multiLine) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < multiLine.getNumGeometries(); i++) {
            LineString lineString = (LineString) multiLine.getGeometryN(i);
            // 获取 LineString 的坐标数据
            Coordinate[] coordinates = lineString.getCoordinates();
            sb.append(buildCoordinatesString(coordinates));
            if (i < multiLine.getNumGeometries() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 判断字符串是否为null
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str){
        return (str==null) || str.trim().equals("");
    }

    public static Object getFieldType(Object object){
        System.out.println(object);
        System.out.println(object.getClass().getSimpleName());
        Object type = null;
//        if(object instanceof Class){
//            type = "Object";
//        }else
        if(object instanceof String){
            // 定义日期正则表达式（匹配 yyyy-MM-dd 格式）
            String datePatternHyphen = "\\d{4}-\\d{2}-\\d{2}";
            String timePatternHyphen = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
            String datePatternSemicolon = "\\d{4}:\\d{2}:\\d{2}";
            String datePatternBackslash = "^\\d{4}/\\d{1,2}/\\d{1,2}$";
            if (((String) object).matches(datePatternHyphen)||((String) object).matches(timePatternHyphen)||((String) object).matches(datePatternSemicolon)||((String) object).matches(datePatternBackslash)) {
                type = "Date";
            } else {
                type = "String";
            }
        }else if(object instanceof Boolean){
            type = "bool";
        }else if(object instanceof Number){
            type = "Number";
        }else {
            type = "Object";
        }
        return type;
    }

    // 生成指定长度的随机字符串
    public static String randomString(int length) {
        // 可以包含在字符串中的字符集合
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 从字符集合中随机选择一个字符
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
