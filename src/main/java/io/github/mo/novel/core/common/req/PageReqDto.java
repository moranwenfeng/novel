package io.github.mo.novel.core.common.req;

/**
 * 分页请求数据格式封装，所有分页请求的Dto类都应该继承该类
 *
 * @author mo
 * @date 2022/5/27 13:17
 */
public class PageReqDto {

    /**
     * 请求页码，默认第一页
     */
    private int pageNum = 1;

    /**
     * 每页大小，默认每页10条
     */
    private int pageSize = 10;

    /**
     * 是否查询所有，默认不查所有
     * 当值为true时 pageNum和 pageSize 无效
     */
    private boolean fetchAll = false;

}
