package com.cqut.weibo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @projectName: Weibo-spring-boot
 * @package: com.cqut.weibo.dto
 * @className: LogDO
 * @description:
 * @author: h1123
 * @createDate: 2022/11/6 22:23
 * @updateUser: h1123
 * @updateDate: 2022/11/6 22:23
 * @updateRemark:
 * @version: v1.0
 * @see :
 */
@Data
@Builder
public class LogDO {
    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParam;

    /**
     * 方法返回值
     */
    private String methodResult;
}
