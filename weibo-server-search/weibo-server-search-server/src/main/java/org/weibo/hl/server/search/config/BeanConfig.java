package org.weibo.hl.server.search.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.search.config
 * @className: BeanConfig
 * @description:
 * @author: hl
 * @createDate: 2023/5/21 17:56
 */

@Configuration
public class BeanConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
