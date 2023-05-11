package org.weibo.hl.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.gateway
 * @className: WeiboGateWayApplication
 * @description:
 * @author: h1123
 * @createDate: 2023/5/10 22:30
 */

@EnableEurekaClient
@Slf4j
@SpringBootApplication
public class WeiboGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeiboGateWayApplication.class, args);
    }
}
