package com.Hugo.goods.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreateBrandParam extends Brand{
    private List<Long> categoryIds;
}
