package com.Hugo.goods.service;

import com.Hugo.goods.pojo.Brand;
import com.Hugo.pojo.PageResult;

import java.util.List;

public interface BrandService {
    PageResult<Brand>queryBrandByPageAndSort(Integer page,Integer rows,String sortBy,Boolean desc,String key);

    void createBrand(Brand brand, List<Long> categoryIds);
}
