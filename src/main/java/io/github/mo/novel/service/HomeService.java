package io.github.mo.novel.service;

import io.github.mo.novel.core.common.resp.RestResp;
import io.github.mo.novel.dto.resp.HomeBookRespDto;

import java.util.List;

/**
 * 首页模块 服务类
 *
 * @author mo
 * @date 2022/6/24 15:50
 */
public interface HomeService {

    /**
     * 查询首页小说推荐列表
     *
     * @return 首页小说推荐列表的 rest响应结果
     */
    RestResp<List<HomeBookRespDto>> listHomeBooks();
}
