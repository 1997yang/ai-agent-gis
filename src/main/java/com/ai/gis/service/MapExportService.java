package com.ai.gis.service;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.ScreenshotType;
import org.springframework.stereotype.Service;

import java.io.File; // 👈 补齐核心的文件判断包
import java.nio.file.Paths;

@Service
public class MapExportService {

    /**
     * 根据空间参数，静默渲染并导出高清地图图片
     * @param lng 中心点经度
     * @param lat 中心点纬度
     * @param zoom 缩放级别
     * @param width 导出图片宽度
     * @param height 导出图片高度
     */
    public void exportGridMap(double lng, double lat, int zoom, int width, int height) {

        // 1. 核心修正一：不仅要文件夹路径，最后必须带上明确的文件名（如 map_result.png）
        String folderPath = "D:\\map_exports\\";
        String fileName = "map_result.png";
        String outputPath = folderPath + fileName; // 拼接后为 D:\map_exports\map_result.png

        // 1. 初始化 Playwright 驱动
        try (Playwright playwright = Playwright.create()) {

            // 2. 核心修正二：防御性拦截。如果 D 盘没有 map_exports 文件夹，代码全自动在硬盘上创建它
            File folder = new File(folderPath);
            if (!folder.exists()) {
                boolean isCreated = folder.mkdirs();
                System.out.println("检测到目标文件夹不存在，系统全自动创建结果: " + isCreated);
            }

            // 3. 启动无头浏览器
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));

            // 4. 创建浏览器上下文，强制设置视口（Viewport）比例， deviceScaleFactor(2) 代表Retina双倍高清
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(width, height)
                    .setDeviceScaleFactor(2)
            );

            Page page = context.newPage();

            // 5. 构造前端 Leaflet 地图的动态访问 URL
            String mapUrl = String.format("http://localhost:3003?lng=%f&lat=%f&zoom=%d", lng, lat, zoom);

            System.out.println("正在请求地图页面: " + mapUrl);
            page.navigate(mapUrl);

            // 6. 等待所有图层、底图网络切片以及 Leaflet 接口完全下载完毕
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // 7. 防御等待 2 秒：确保平滑动画完全静止，字体和网格数字完美就绪
            page.waitForTimeout(2000);

            // 8. 核心修正三：此时 Paths.get(outputPath) 拿到的包含了文件名，Playwright 即可顺利写入
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(outputPath))
                    .setType(ScreenshotType.PNG)
            );

            System.out.println("🎉 [Playwright] 高清格网地图图片下载成功，已安全保存至: " + outputPath);

            // 9. 释放资源
            context.close();
            browser.close();
        } catch (Exception e) {
            System.err.println("❌ 地图导出发生异常失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}