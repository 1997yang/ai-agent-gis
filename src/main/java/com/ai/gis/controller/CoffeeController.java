package com.ai.gis.controller;

import com.ai.gis.common.Result;
import com.ai.gis.entity.Print;
import com.ai.gis.service.MapExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/service")
@RestController
public class CoffeeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapExportService mapExportService;

    @GetMapping("/getCoffeesList")
    public Result getCoffeesList(@RequestParam(required = false, defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "1") Integer pageSize, @RequestParam(required = false) String name) {
        Result result = Result.ok(11);
        return result;
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }
    @PostMapping("/exportGrid")
    public String exportGrid(@RequestBody Print print) {
        System.out.print("__________________________");
        mapExportService.exportGridMap(print.getLng(),print.getLat(),print.getZoom(),print.getWidth(),print.getHeight());
        return "test";
    }
}