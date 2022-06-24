package io.github.mo.novel.service.impl;

import io.github.mo.novel.core.common.resp.RestResp;
import io.github.mo.novel.dto.resp.HomeBookRespDto;
import io.github.mo.novel.manager.HomeBookCacheManager;
import io.github.mo.novel.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页模块 服务实现类
 *
 * @author mo
 * @date 2022/6/24 15:53
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeBookCacheManager homeBookCacheManager;


    @Override
    public RestResp<List<HomeBookRespDto>> listHomeBooks() {
        return RestResp.ok(homeBookCacheManager.listHomeBooks());
    }
}
