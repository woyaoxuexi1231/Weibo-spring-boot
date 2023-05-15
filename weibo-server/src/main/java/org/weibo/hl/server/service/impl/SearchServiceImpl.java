package org.weibo.hl.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.weibo.hl.core.consts.MessageConsts;
import org.weibo.hl.core.pojo.search.SearchMsgDTO;
import org.weibo.hl.security.api.pojo.req.UserInfoQueryReqDTO;
import org.weibo.hl.security.api.pojo.rsp.UserCommonInfoDTO;
import org.weibo.hl.server.model.req.SearchReqDTO;
import org.weibo.hl.server.model.rsp.SearchRspDTO;
import org.weibo.hl.server.service.SearchFeignService;
import org.weibo.hl.server.service.SearchService;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.service.impl
 * @className: SearchServiceImpl
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 21:52
 */

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Resource
    SearchFeignService searchFeignService;

    @Override
    public SearchRspDTO getSearchResult(SearchReqDTO req) {

        // 1. 获取用户信息
        log.info("获取用户信息");
        UserInfoQueryReqDTO userReq = new UserInfoQueryReqDTO();
        userReq.setUsername(req.getKeyWord());
        // Map userReq = new HashMap();
        // userReq.put("username",req.getKeyWord());
        // String reqs =  JSONObject.toJSONString(userReq);
        // List<UserCommonInfoDTO> userRsp = restTemplate.getForObject("http://weibo-security/getUserInfo", List.class, reqs);
        List<UserCommonInfoDTO> userRsp = restTemplate.postForObject("http://weibo-security/getUserInfo", userReq, List.class);
        // List<UserCommonInfoDTO> userRsp = searchFeignService.getUserInfo(userReq);

        // 把这个搜索消息发送到 MQ, 供其他应用消费
        try {
            SearchMsgDTO msgDTO = new SearchMsgDTO();
            msgDTO.setKeyWord(req.getKeyWord());
            MessagePostProcessor messagePostProcessor = message -> {
                // 设置消息的过期时间
                // message.getMessageProperties().setExpiration("30000");
                return message;
            };
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(
                    MessageConsts.SEARCH_EXCHANGE,
                    MessageConsts.SEARCH_ROUTE_KEY,
                    JSONObject.toJSON(msgDTO).toString(),
                    messagePostProcessor,
                    correlationData);
        } catch (Exception e) {
            log.error("消息发送异常!", e);
        }

        // 结果
        SearchRspDTO searchRsp = new SearchRspDTO();
        searchRsp.setUserCommonInfoDTOS(userRsp);
        return searchRsp;
    }
}
