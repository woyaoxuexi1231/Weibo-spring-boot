package com.cqut.weibo.aop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Value("${server.resources.static-locations}")
    private String path;

    @Value("${server.mvc.static-path-pattern}")
    private String apiPath;

    /**
     * 配置本地资源映射
     *
     * @Param:
     * @Return:
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(apiPath)  //请求路径
                .addResourceLocations(path);        //本地地址
    }
}