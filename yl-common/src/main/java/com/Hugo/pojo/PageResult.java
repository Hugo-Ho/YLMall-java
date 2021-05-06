package com.Hugo.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T> {
    private Long total;//总条数
    private Long totalPage;//总页数
    private List<T> list;//当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageResult(Long total, Long totalPage, List<T> list) {
        this.total = total;
        this.totalPage = totalPage;
        this.list = list;
    }
}
