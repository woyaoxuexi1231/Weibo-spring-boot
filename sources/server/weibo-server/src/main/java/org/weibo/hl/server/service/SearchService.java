package org.weibo.hl.server.service;

import org.weibo.hl.server.model.req.SearchReqDTO;
import org.weibo.hl.server.model.rsp.SearchRspDTO;

import java.util.List;

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
