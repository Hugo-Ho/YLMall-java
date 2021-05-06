package com.Hugo.goods.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Category {
    private long id;
    private String name;
    private long    parentId;
    private Boolean isParent;//注意isParent生成的getter和setter方法需要手动加上Is
    private Integer sort;

}
