package org.weibo.hl.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.weibo.hl.gateway.filter.SecurityFilter;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.gateway.config
 * @className: RouteLocator
 * @description:
 * @author: hl
 * @createDate: 2023/5/21 14:01
 */

public class BeanConfig {

    // @Bean
    // public RouteLocator customerRouteLocator(RouteLocatorBuilder builder){
    //     return builder.routes()
    //             .route(r->r.path("/**/**").filters(f->f.filter(new SecurityFilter())).
    //             )
    // }
}
