package com.Hugo.goods.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Brand {
    private Long id;
    private String name;
    private String image;
}
