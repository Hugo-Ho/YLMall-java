package com.Hugo.pojo;

import com.sun.javafx.image.IntPixelGetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Data
@Accessors(chain = true)
public class PageParam<T> {
    private Integer page;//当前页
    private Integer rows;//每页大小
    private String sortBy;//排序字段
    private Boolean desc;//是否为降序
    private String key;//搜索关键词
    private T param;//业务参数
}
