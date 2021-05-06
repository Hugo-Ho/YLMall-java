package com.Hugo.goods.pojo.VO;

import com.Hugo.goods.pojo.Spu;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpuVO extends Spu {

    private String cname;// 商品分类名称

    private String bname;// 品牌名称
}
