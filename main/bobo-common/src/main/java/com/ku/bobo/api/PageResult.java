package com.ku.bobo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @Date 2020/4/30 10:20
 * @Created by xb
 */
public class PageResult<T> implements Serializable {


    private Long pageNum; //第几页
    private Long pageSize; // 一页多少条数据

    private List<T> rows;


    public PageResult(Long pageNum, Long pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageResult( List<T> rows , Long pageNum ) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.rows = rows;
    }

    public PageResult(Long pageNum, Long pageSize, List<T> rows) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
