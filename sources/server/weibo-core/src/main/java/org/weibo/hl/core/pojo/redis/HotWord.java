package org.weibo.hl.core.pojo.redis;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.core.pojo.redis
 * @className: HotWord
 * @description:
 * @author: h1123
 * @createDate: 2023/5/11 0:15
 */

@Data
@AllArgsConstructor
public class HotWord {

    String value;

    Integer score;
}
