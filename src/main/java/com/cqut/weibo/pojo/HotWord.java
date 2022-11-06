package com.cqut.weibo.pojo;

/**
 * @Description:热搜词汇
 * @Author:HL
 * @date:2021.04.26.16.55.36
 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotWord {

    String value;

    int score;

}
