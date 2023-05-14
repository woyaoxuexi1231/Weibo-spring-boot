package org.weibo.hl.server.search.api.service;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.search.api.service
 * @className: TopHubService
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 20:16
 */

public interface TopHubService {

    /**
     * 新增热搜词汇
     */
    void addHotWord(String hotWord);
}
