package org.weibo.hl.server.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.search
 * @className: WeiboServerSearchApplication
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 19:44
 */

@EnableEurekaClient
@SpringBootApplication
public class WeiboServerSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeiboServerSearchApplication.class, args);
    }
}
