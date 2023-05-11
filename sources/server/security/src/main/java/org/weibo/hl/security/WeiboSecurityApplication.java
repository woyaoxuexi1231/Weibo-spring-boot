package org.weibo.hl.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.security
 * @className: WeiboSecurtityApplication
 * @description:
 * @author: h1123
 * @createDate: 2023/5/10 0:11
 */

@MapperScan("org.weibo.hl.security.mapper")
@EnableEurekaClient
@SpringBootApplication
public class WeiboSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(WeiboSecurityApplication.class, args);
    }
}
