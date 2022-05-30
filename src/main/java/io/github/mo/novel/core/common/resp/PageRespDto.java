package io.github.mo.novel.core.common.resp;

import java.util.List;

/**
 * 分页响应数据格式封装
 *
 * @author mo
 * @date 2022/5/27 13:24
 */
public class PageRespDto<T> {

    /**
     * 页码
     */
    private final long pageNum;

    /**
     * 每页大小
     */
    private final long pageSzie;

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 分页数据集
     */

    private final List<? extends T> list;

    /**
     * 该构造函数用于通用分页查询的场景
     * 接收普通分页数据和普通集合
     */
    public PageRespDto(long pageNum, long pageSzie, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSzie = pageSzie;
        this.total = total;
        this.list = list;
    }

    public static <T> PageRespDto<T> of(long pageNum, long pageSzie, long total, List<T> list) {
        return new PageRespDto<>(pageNum, pageSzie, total, list);
    }

    /**
     * 获取分页数
     */
    public long getPages() {
        if(this.pageSzie == 0L) {
            return 0L;
        }else {
            long pages = this.total / this.pageSzie;
            if (this.total % this.pageSzie != 0L) {
                ++pages;
            }
            return pages;
        }
    }
}
