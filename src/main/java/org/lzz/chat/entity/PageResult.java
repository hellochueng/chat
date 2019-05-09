package org.lzz.chat.entity;

/**
 * 具有分页的结果
 */
public class PageResult extends Result {

    private Integer page;

    private Long count;

    private Integer pageTotal;

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
