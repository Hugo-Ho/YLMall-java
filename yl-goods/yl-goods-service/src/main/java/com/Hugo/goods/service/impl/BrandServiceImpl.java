package com.Hugo.goods.service.impl;

import com.Hugo.goods.mapper.BrandMapper;
import com.Hugo.goods.mapper.CategoryBrandMapper;
import com.Hugo.goods.pojo.Brand;
import com.Hugo.goods.service.BrandService;
import com.Hugo.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //开始分页
        PageHelper.startPage(page, rows);
        //过滤
//        Example example = new Example(Brand.class);
//        if (StringUtils.isNotBlank(key)) {
//            example.createCriteria().andLike("name", "%" + key + "%")
//                    .orEqualTo("letter", key);
//        }
//        if (StringUtils.isNotBlank(sortBy)) {
//            // 排序
//            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
//            example.setOrderByClause(orderByClause);
//        }
        //查询
        List<Brand> list = brandMapper.queryBrandList();
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return new PageResult<Brand>(pageInfo.getTotal(), (long) pageInfo.getPageNum(), pageInfo.getList());

    }

    @Override
    public void createBrand(Brand brand, List<Long> categoryIds) {
        //新增品牌名称
        this.brandMapper.insertBrand(brand);
        //新增品牌和分类关系
        this.categoryBrandMapper.insertCategoryBrand(categoryIds, brand.getId());
    }
}
