package org.weibo.hl.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server
 * @className: WeiboServerApplication
 * @description:
 * @author: h1123
 * @createDate: 2023/5/11 0:06
 */

@EnableEurekaClient
@SpringBootApplication
public class WeiboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeiboServerApplication.class, args);
    }
}
