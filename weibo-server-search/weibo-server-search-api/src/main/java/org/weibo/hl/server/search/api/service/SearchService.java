package org.weibo.hl.server.search.api.service;

import org.weibo.hl.server.search.api.model.req.SearchReqDTO;
import org.weibo.hl.server.search.api.model.rsp.SearchRspDTO;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.service
 * @className: SearchService
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 21:16
 */

public interface SearchService {

    SearchRspDTO getSearchResult(SearchReqDTO req);
}
