package com.ai.gis.entity;

public class Print {
    private Double lng;
    private Double lat;
    private Integer zoom;
    private Integer width;
    private Integer height;
    private String outputPath;
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}