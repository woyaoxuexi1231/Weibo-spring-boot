package org.weibo.hl.server.search.api.model.rsp;

import lombok.Data;
import org.weibo.hl.security.api.pojo.rsp.UserCommonInfoDTO;

import java.util.List;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.model.rsp
 * @className: SearchRspDTO
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 21:19
 */

@Data
public class SearchRspDTO {

    List<UserCommonInfoDTO> userCommonInfoDTOS;
}
