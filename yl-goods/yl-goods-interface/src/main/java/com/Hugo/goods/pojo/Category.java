package com.Hugo.goods.pojo;

import rx.internal.reactivestreams.PublisherAsCompletable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long    parentId;
    private Boolean isParent;//注意isParent生成的getter和setter方法需要手动加上Is
    private Integer sort;

    public long getId()
    {
        return  id;
    }

    public String getName()
    {
        return name;
    }

    public long getParentId()
    {
        return parentId;
    }

    public Boolean getIsParent()
    {
        return isParent;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setId(long id)
    {
        this.id=id;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setParentId(long parentId)
    {
        this.parentId=parentId;
    }
    public void setIsParent(Boolean isParent)
    {
        this.isParent=isParent;
    }

    public void setSort(Integer sort)
    {
        this.sort=sort;
    }
}
