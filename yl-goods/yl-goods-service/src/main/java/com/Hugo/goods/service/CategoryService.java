package com.Hugo.goods.service;

import com.Hugo.goods.pojo.Brand;
import com.Hugo.goods.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoryListByParentId(Long pid);

    List<Category> queryCategoryListByBrandId(Long bid);
}
