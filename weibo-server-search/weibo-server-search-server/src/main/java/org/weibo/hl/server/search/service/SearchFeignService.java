package org.weibo.hl.server.search.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.weibo.hl.security.api.pojo.req.UserInfoQueryReqDTO;
import org.weibo.hl.security.api.pojo.rsp.UserCommonInfoDTO;
import org.weibo.hl.server.search.config.FeignConfig;

import java.util.List;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.service
 * @className: SearchFeignService
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 23:46
 */

@FeignClient(value = "weibo-security", configuration = FeignConfig.class)
public interface SearchFeignService {

    @GetMapping("/getUserInfo")
    public List<UserCommonInfoDTO> getUserInfo(@RequestParam(value = "req") UserInfoQueryReqDTO req);
}
