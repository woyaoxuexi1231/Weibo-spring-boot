package org.weibo.hl.server.search.mq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.weibo.hl.core.consts.MessageConsts;
import org.weibo.hl.core.pojo.search.SearchMsgDTO;
import org.weibo.hl.server.search.api.service.TopHubService;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.search.mq
 * @className: TopHubLinstener
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 22:42
 */

@Slf4j
@Component
public class TopHubListener {

    public static final String QUEUE_NAME = "top-hub-queue";

    public static final String ROUTE_KEY = "topic.search.*";

    @Resource
    TopHubService topHubService;

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    name = QUEUE_NAME,
                    durable = "true",
                    autoDelete = "false"
                    // arguments = {@Argument(name = "x-dead-letter-exchange", value = MQConfig.DEAD_EXCHANGE_NAME)}),
            ),
            exchange = @Exchange(
                    value = MessageConsts.SEARCH_EXCHANGE,
                    type = ExchangeTypes.TOPIC,
                    autoDelete = "false",
                    durable = "true"),
            key = ROUTE_KEY
    ))
    public void receiveMsg(Message msg, Channel channel) {

        try {
            log.info("收到消息: {}", msg);
            SearchMsgDTO validation = JSONObject.parseObject(new String(msg.getBody(), StandardCharsets.UTF_8), SearchMsgDTO.class);
            topHubService.addHotWord(validation.getKeyWord());
        } catch (Exception e) {
            log.info("消息消费失败! ", e);
        } finally {
            try {
                channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                log.error("消息应答失败! ", e);
            }
        }
    }


}
