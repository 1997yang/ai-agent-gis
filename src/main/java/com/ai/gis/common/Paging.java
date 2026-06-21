package com.ai.gis.common;
/**
 * 分页类
 */

import lombok.Data;

@Data
public class Paging<T> {
    private Object data;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}