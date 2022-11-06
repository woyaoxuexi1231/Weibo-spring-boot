package com.cqut.weibo.dao;

import com.cqut.weibo.dto.LogDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @projectName: Weibo-spring-boot
 * @package: com.cqut.weibo.dao
 * @className: LogMapper
 * @description:
 * @author: h1123
 * @createDate: 2022/11/6 22:30
 * @updateUser: h1123
 * @updateDate: 2022/11/6 22:30
 * @updateRemark:
 * @version: v1.0
 * @see :
 */
@Mapper
@Component
public interface LogMapper {
    void insert(LogDO logDO);
}
