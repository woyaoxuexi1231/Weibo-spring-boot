package org.weibo.hl.core.pojo.search;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.core.pojo.redis
 * @className: HotWord
 * @description:
 * @author: hl
 * @createDate: 2023/5/11 0:15
 */

@Data
@AllArgsConstructor
public class HotWord {

    String value;

    Integer score;
}
