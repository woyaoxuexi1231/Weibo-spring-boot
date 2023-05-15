package org.weibo.hl.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.weibo.hl.core.consts.MessageConsts;

import javax.annotation.PostConstruct;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.config
 * @className: InitConfig
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 22:47
 */

@Slf4j
@Configuration
public class MessageInitConfig {

    @Autowired
    RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void init() {
        // 声明交换机
        rabbitAdmin.declareExchange(exchange());
        log.info("创建交换机 {} 成功! ", exchange());
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    public Exchange exchange() {
        return new TopicExchange(
                MessageConsts.SEARCH_EXCHANGE,
                true,
                false,
                null);
    }

}
