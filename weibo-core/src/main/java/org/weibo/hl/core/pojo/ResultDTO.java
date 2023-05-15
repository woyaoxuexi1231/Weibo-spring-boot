package org.weibo.hl.core.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.core.pojo.search
 * @className: ResultDTO
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 21:03
 */

@Data
public class ResultDTO<E> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 结果
     */
    private E data;

}
