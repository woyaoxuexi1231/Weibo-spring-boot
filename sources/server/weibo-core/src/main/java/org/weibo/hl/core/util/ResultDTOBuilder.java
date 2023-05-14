package org.weibo.hl.core.util;

import org.weibo.hl.core.pojo.ResultDTO;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.core.util
 * @className: ResultDTOBuilder
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 21:06
 */

public class ResultDTOBuilder {

    public static ResultDTO<?> resultDefaultBuild() {
        ResultDTO<?> resultDTO = new ResultDTO<>();
        resultDTO.setCode(200);
        resultDTO.setMsg("success");
        return resultDTO;
    }

    public static <Rsp> ResultDTO<Rsp> resultSuccessBuild(Rsp rsp) {
        ResultDTO<Rsp> resultDTO = new ResultDTO<>();
        resultDTO.setCode(200);
        resultDTO.setMsg("success");
        resultDTO.setData(rsp);
        return resultDTO;
    }

    public static <Rsp> ResultDTO<Rsp> resultErrorBuild(String msg) {
        ResultDTO<Rsp> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        resultDTO.setMsg(msg);
        return resultDTO;
    }

    public static <Rsp> ResultDTO<Rsp> resultErrorBuild(int code, String msg) {
        ResultDTO<Rsp> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        resultDTO.setMsg(msg);
        return resultDTO;
    }

}
