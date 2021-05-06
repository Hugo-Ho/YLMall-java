package com.Hugo.goods.service.impl;

import com.Hugo.goods.mapper.CategoryBrandMapper;
import com.Hugo.goods.mapper.CategoryMapper;
import com.Hugo.goods.pojo.Category;
import com.Hugo.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    public List<Category> queryCategoryListByParentId(Long pid)
    {
        return this.categoryMapper.queryCategoryListByParentId(pid);
    }

    public List<Category> queryCategoryListByBrandId(Long bid)
    {
        List<Long>categoryIds = this.categoryBrandMapper.queryCategoryIdsByBarandId(bid);
        if(categoryIds==null||categoryIds.size()<0)
        {
            return null;
        }
        return this.categoryMapper.queryCategoryListByCategoryIds(categoryIds);
    }
}
