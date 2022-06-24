package io.github.mo.novel.controller.front;

import io.github.mo.novel.core.common.constant.ApiRouterConsts;
import io.github.mo.novel.core.common.resp.RestResp;
import io.github.mo.novel.dto.resp.HomeBookRespDto;
import io.github.mo.novel.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页模块 API 接口
 *
 * @author mo
 * @date 2022/6/24 16:03
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_HOME_URL_PREFIX)
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    /**
     * 首页小说推荐查询接口
     */
    public RestResp<List<HomeBookRespDto>> listRestResp() {
        return homeService.listHomeBooks();
    }

}
