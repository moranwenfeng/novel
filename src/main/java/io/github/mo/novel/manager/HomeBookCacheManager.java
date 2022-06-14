package io.github.mo.novel.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.mo.novel.core.common.constant.CacheConsts;
import io.github.mo.novel.core.constant.DatabaseConsts;
import io.github.mo.novel.dao.entity.BookInfo;
import io.github.mo.novel.dao.entity.HomeBook;
import io.github.mo.novel.dao.mapper.BookInfoMapper;
import io.github.mo.novel.dao.mapper.HomeBookMapper;
import io.github.mo.novel.dto.resp.HomeBookRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 首页推荐小说 缓存管理类
 *
 * @author mo
 * @date 2022/6/12 16:18
 */
@Component
@RequiredArgsConstructor
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询首页小说推荐并放入缓存
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> listHomeBooks() {
        //从首页小说推荐表中查询出需要推荐的小说
        List<HomeBook> homeBooks = homeBookMapper.selectList(null);

        //获取推荐小说ID列表
        if(!CollectionUtils.isEmpty(homeBooks)){
            List<Long> booksId = homeBooks.stream().map(HomeBook::getBookId).toList();

            //根据小说ID列表查询相关的小说信息列表
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
            bookInfoQueryWrapper.in(DatabaseConsts.CommonColumnEnum.ID.getName(),booksId);
            List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);

            //组装HomeBookRespDto 列表数据并返回
            if(!CollectionUtils.isEmpty(bookInfos)) {
                Map<Long, BookInfo> bookInfoMap = bookInfos.stream().collect(Collectors.toMap(BookInfo::getId, Function.identity()));

                return homeBooks.stream().map(v -> {
                    BookInfo bookInfo = bookInfoMap.get(v.getBookId());
                    HomeBookRespDto bookRespDto = new HomeBookRespDto();
                    bookRespDto.setBookId(v.getBookId());
                    bookRespDto.setBookName(bookInfo.getBookName());
                    bookRespDto.setPicUrl(bookInfo.getPicUrl());
                    bookRespDto.setAuthorName(bookInfo.getAuthorName());
                    bookRespDto.setBookDesc(bookInfo.getBookDesc());
                    return  bookRespDto;
                }).toList();
            }
        }

        return Collections.emptyList();
    }
}
