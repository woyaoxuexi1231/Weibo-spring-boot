// package com.cqut.weibo.aop;
//
// import com.cqut.weibo.config.AppConfig;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
// import javax.annotation.Resource;
//
// @Configuration
// public class MyWebAppConfiguration extends WebMvcConfigurerAdapter {
//
//     @Resource
//     private AppConfig appConfig;
//
//     @Value("${server.resources.static-locations}")
//     private String path;
//
//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         String path = appConfig.getUploadFolder();
//         registry.addResourceHandler("/image/**").addResourceLocations("file://" + path);
//         super.addResourceHandlers(registry);
//     }
// }
