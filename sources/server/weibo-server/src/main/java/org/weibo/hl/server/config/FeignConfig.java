package org.weibo.hl.server.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.config
 * @className: FeignConfig
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 23:51
 */


@Configuration
public class FeignConfig {

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }
}
