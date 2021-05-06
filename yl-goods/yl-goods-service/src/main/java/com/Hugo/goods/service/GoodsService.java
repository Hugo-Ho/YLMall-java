package com.Hugo.goods.service;

import com.Hugo.goods.pojo.VO.SpuVO;
import com.Hugo.pojo.PageResult;

public interface GoodsService {
    PageResult<SpuVO> querySpuByPageAndSort(Integer page, Integer rows, String key);
}
