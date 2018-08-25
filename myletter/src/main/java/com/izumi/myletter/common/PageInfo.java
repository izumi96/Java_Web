package com.izumi.myletter.common;


import java.util.List;

public class PageInfo<T> {
    //一页的大小
    private int pageSize;
    
    //当前页码
    private int currentPage;
    
    //总页数
    private int pageCount;
    
    //总记录数
    private long total;
    
    //是否有上一页
    private boolean hasPreviousPage;
    
    //是否有下一页
    private boolean hasNextPage;
    
    //结果列表
    private List<T> result;
    
    public PageInfo(int currentPage, int pageSize, long total, List<T> result) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.result = result;
        this.pageCount = 0;
        if (pageSize > 0) {
            //用来看有没有余数的
            long num = total / pageSize;
            long pageNum = total % pageSize > 0 ? num + 1 : num;
            this.pageCount = Integer.valueOf(String.valueOf(pageNum));
        }
        this.hasNextPage = pageCount - this.currentPage > 0;
        this.hasPreviousPage = this.currentPage > 1;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getPageCount() {
        return pageCount;
    }
    
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }
    
    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }
    
    public boolean isHasNextPage() {
        return hasNextPage;
    }
    
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
    
    public List<T> getResult() {
        return result;
    }
    
    public void setResult(List<T> result) {
        this.result = result;
    }
    
}
