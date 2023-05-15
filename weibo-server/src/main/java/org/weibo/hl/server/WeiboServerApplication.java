package org.weibo.hl.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server
 * @className: WeiboServerApplication
 * @description:
 * @author: hl
 * @createDate: 2023/5/11 0:06
 */

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class WeiboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeiboServerApplication.class, args);
    }
}
